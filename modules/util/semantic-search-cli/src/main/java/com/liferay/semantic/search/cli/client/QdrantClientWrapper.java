/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.client;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.semantic.search.cli.util.Chunk;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import io.qdrant.client.grpc.JsonWithInt;
import io.qdrant.client.grpc.Points;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Thin wrapper around io.qdrant:client for this tool's vector ops.
 * Stays lazy — the gRPC channel opens on first call so the `status`
 * subcommand can fail fast with a clear unreachable message before
 * incurring connection cost.
 *
 * @author JR Houn
 */
public class QdrantClientWrapper {

	public static final String COLLECTION = "liferay_learn_docs";

	public static final String META_COLLECTION = "liferay_learn_meta";

	public boolean collectionExists(String name) throws Exception {
		QdrantClient qdrantClient = _client();

		List<String> names = qdrantClient.listCollectionsAsync(
		).get();

		return names.contains(name);
	}

	public void deleteByRelPaths(List<String> relPaths) throws Exception {
		if (relPaths.isEmpty()) {
			return;
		}

		QdrantClient qdrantClient = _client();

		for (String relPath : relPaths) {
			Points.Filter filter = Points.Filter.newBuilder(
			).addMust(
				Points.Condition.newBuilder(
				).setField(
					Points.FieldCondition.newBuilder(
					).setKey(
						"rel_path"
					).setMatch(
						Points.Match.newBuilder(
						).setKeyword(
							relPath
						).build()
					).build()
				).build()
			).build();

			qdrantClient.deleteAsync(
				COLLECTION, filter
			).get();
		}
	}

	public void deleteMetaFiles(List<String> relPaths) throws Exception {
		if (relPaths.isEmpty()) {
			return;
		}

		QdrantClient qdrantClient = _client();

		List<Points.PointId> ids = new ArrayList<>();

		for (String relPath : relPaths) {
			UUID uuid = _metaFileRecordId(relPath);

			ids.add(
				Points.PointId.newBuilder(
				).setUuid(
					uuid.toString()
				).build());
		}

		qdrantClient.deleteAsync(
			META_COLLECTION, ids
		).get();
	}

	public void ensureCollection(String name, int vectorSize) throws Exception {
		QdrantClient qdrantClient = _client();

		List<String> existing = qdrantClient.listCollectionsAsync(
		).get();

		if (!existing.contains(name)) {
			qdrantClient.createCollectionAsync(
				name,
				Collections.VectorParams.newBuilder(
				).setSize(
					vectorSize
				).setDistance(
					Collections.Distance.Cosine
				).build()
			).get();

			if (name.equals(COLLECTION)) {
				qdrantClient.createPayloadIndexAsync(
					COLLECTION, "rel_path",
					Collections.PayloadSchemaType.Keyword, null, null, null,
					null
				).get();
			}
		}
	}

	public int getChunkCount() throws Exception {
		QdrantClient qdrantClient = _client();

		Collections.CollectionInfo info = qdrantClient.getCollectionInfoAsync(
			COLLECTION
		).get();

		return (int)info.getPointsCount();
	}

	public String getQdrantUrl() {
		return StringBundler.concat("http://", _host(), ":", _port());
	}

	public boolean isReachable() {
		try {
			QdrantClient qdrantClient = _client();

			qdrantClient.listCollectionsAsync(
			).get();

			return true;
		}
		catch (Exception exception) {
			return false;
		}
	}

	public Map<String, String> loadFileHashes() throws Exception {
		QdrantClient qdrantClient = _client();

		Points.Filter filter = Points.Filter.newBuilder(
		).addMust(
			Points.Condition.newBuilder(
			).setField(
				Points.FieldCondition.newBuilder(
				).setKey(
					"kind"
				).setMatch(
					Points.Match.newBuilder(
					).setKeyword(
						"file"
					).build()
				).build()
			).build()
		).build();

		Map<String, String> hashes = new HashMap<>();

		Points.PointId offset = null;

		while (true) {
			Points.ScrollPoints.Builder builder =
				Points.ScrollPoints.newBuilder(
				).setCollectionName(
					META_COLLECTION
				).setFilter(
					filter
				).setLimit(
					512
				).setWithPayload(
					Points.WithPayloadSelector.newBuilder(
					).setEnable(
						true
					).build()
				);

			if (offset != null) {
				builder.setOffset(offset);
			}

			Points.ScrollResponse response = qdrantClient.scrollAsync(
				builder.build()
			).get();

			for (Points.RetrievedPoint point : response.getResultList()) {
				Map<String, JsonWithInt.Value> payload = point.getPayloadMap();

				JsonWithInt.Value relPathValue = payload.get("rel_path");

				JsonWithInt.Value sha256Value = payload.get("sha256");

				if ((relPathValue != null) && (sha256Value != null)) {
					hashes.put(
						relPathValue.getStringValue(),
						sha256Value.getStringValue());
				}
			}

			if (!response.hasNextPageOffset()) {
				break;
			}

			offset = response.getNextPageOffset();
		}

		return hashes;
	}

	public MetaState readMetaState() throws Exception {
		QdrantClient qdrantClient = _client();

		Points.ScrollResponse response = qdrantClient.scrollAsync(
			Points.ScrollPoints.newBuilder(
			).setCollectionName(
				META_COLLECTION
			).setFilter(
				Points.Filter.newBuilder(
				).addMust(
					Points.Condition.newBuilder(
					).setField(
						Points.FieldCondition.newBuilder(
						).setKey(
							"kind"
						).setMatch(
							Points.Match.newBuilder(
							).setKeyword(
								"state"
							).build()
						).build()
					).build()
				).build()
			).setLimit(
				1
			).setWithPayload(
				Points.WithPayloadSelector.newBuilder(
				).setEnable(
					true
				).build()
			).build()
		).get();

		List<Points.RetrievedPoint> points = response.getResultList();

		if (points.isEmpty()) {
			return null;
		}

		Points.RetrievedPoint point = points.get(0);

		Map<String, JsonWithInt.Value> payload = point.getPayloadMap();

		JsonWithInt.Value lastIngest = payload.get("last_ingest");

		JsonWithInt.Value docCount = payload.get("doc_count");

		return new MetaState(
			(lastIngest != null) ? lastIngest.getStringValue() : null,
			(docCount != null) ? (int)docCount.getIntegerValue() : 0);
	}

	public List<Hit> search(float[] vector, int limit) throws Exception {
		QdrantClient qdrantClient = _client();

		List<Float> vectorList = new ArrayList<>(vector.length);

		for (float f : vector) {
			vectorList.add(f);
		}

		List<Points.ScoredPoint> scoredPoints = qdrantClient.searchAsync(
			Points.SearchPoints.newBuilder(
			).setCollectionName(
				COLLECTION
			).addAllVector(
				vectorList
			).setLimit(
				limit
			).setWithPayload(
				Points.WithPayloadSelector.newBuilder(
				).setEnable(
					true
				).build()
			).build()
		).get();

		List<Hit> hits = new ArrayList<>();

		for (Points.ScoredPoint scored : scoredPoints) {
			Map<String, JsonWithInt.Value> payload = scored.getPayloadMap();

			List<String> headingPath = new ArrayList<>();

			JsonWithInt.Value hp = payload.get("heading_path");

			if ((hp != null) && hp.hasListValue()) {
				JsonWithInt.ListValue listValue = hp.getListValue();

				for (JsonWithInt.Value entry : listValue.getValuesList()) {
					headingPath.add(entry.getStringValue());
				}
			}

			JsonWithInt.Value relPathValue = payload.get("rel_path");
			JsonWithInt.Value chunkIdValue = payload.get("chunk_id");
			JsonWithInt.Value textValue = payload.get("text");

			hits.add(
				new Hit(
					relPathValue.getStringValue(), scored.getScore(),
					chunkIdValue.getStringValue(), textValue.getStringValue(),
					headingPath));
		}

		return hits;
	}

	public void upsertChunks(List<Chunk> chunks, float[][] vectors)
		throws Exception {

		if (chunks.isEmpty()) {
			return;
		}

		QdrantClient qdrantClient = _client();

		List<Points.PointStruct> points = new ArrayList<>();

		for (int i = 0; i < chunks.size(); i++) {
			Chunk chunk = chunks.get(i);

			List<Float> vector = new ArrayList<>(vectors[i].length);

			for (float f : vectors[i]) {
				vector.add(f);
			}

			JsonWithInt.ListValue.Builder listBuilder =
				JsonWithInt.ListValue.newBuilder();

			for (String heading : chunk.headingPath()) {
				listBuilder.addValues(_stringValue(heading));
			}

			Map<String, JsonWithInt.Value> payload =
				HashMapBuilder.<String, JsonWithInt.Value>put(
					"chunk_id", _stringValue(chunk.chunkId())
				).put(
					"heading_path",
					JsonWithInt.Value.newBuilder(
					).setListValue(
						listBuilder.build()
					).build()
				).put(
					"rel_path", _stringValue(chunk.relPath())
				).put(
					"text", _stringValue(chunk.text())
				).build();

			UUID pointId = chunk.pointId();

			points.add(
				Points.PointStruct.newBuilder(
				).setId(
					Points.PointId.newBuilder(
					).setUuid(
						pointId.toString()
					).build()
				).setVectors(
					Points.Vectors.newBuilder(
					).setVector(
						Points.Vector.newBuilder(
						).addAllData(
							vector
						).build()
					).build()
				).putAllPayload(
					payload
				).build());
		}

		qdrantClient.upsertAsync(
			COLLECTION, points
		).get();
	}

	public void writeMetaFile(String relPath, String sha256) throws Exception {
		QdrantClient qdrantClient = _client();

		Map<String, JsonWithInt.Value> payload =
			HashMapBuilder.<String, JsonWithInt.Value>put(
				"kind", _stringValue("file")
			).put(
				"rel_path", _stringValue(relPath)
			).put(
				"sha256", _stringValue(sha256)
			).build();

		UUID pointId = _metaFileRecordId(relPath);

		qdrantClient.upsertAsync(
			META_COLLECTION,
			Arrays.asList(
				Points.PointStruct.newBuilder(
				).setId(
					Points.PointId.newBuilder(
					).setUuid(
						pointId.toString()
					).build()
				).setVectors(
					Points.Vectors.newBuilder(
					).setVector(
						Points.Vector.newBuilder(
						).addData(
							0.0F
						).build()
					).build()
				).putAllPayload(
					payload
				).build())
		).get();
	}

	public void writeMetaState(String rootPath, int docCount) throws Exception {
		QdrantClient qdrantClient = _client();

		qdrantClient.upsertAsync(
			META_COLLECTION,
			Arrays.asList(
				Points.PointStruct.newBuilder(
				).setId(
					Points.PointId.newBuilder(
					).setUuid(
						_metaStateRecordId().toString()
					).build()
				).setVectors(
					Points.Vectors.newBuilder(
					).setVector(
						Points.Vector.newBuilder(
						).addData(
							0.0F
						).build()
					).build()
				).putAllPayload(
					HashMapBuilder.<String, JsonWithInt.Value>put(
						"doc_count",
						JsonWithInt.Value.newBuilder(
						).setIntegerValue(
							docCount
						).build()
					).put(
						"ingest_root", _stringValue(rootPath)
					).put(
						"kind", _stringValue("state")
					).put(
						"last_ingest",
						() -> _stringValue(
							OffsetDateTime.now(
								ZoneOffset.UTC
							).format(
								DateTimeFormatter.ISO_OFFSET_DATE_TIME
							))
					).build()
				).build())
		).get();
	}

	public static class Hit {

		public Hit(
			String relPath, double score, String chunkId, String text,
			List<String> headingPath) {

			_relPath = relPath;
			_score = score;
			_chunkId = chunkId;
			_text = text;
			_headingPath = headingPath;
		}

		public String chunkId() {
			return _chunkId;
		}

		public List<String> headingPath() {
			return _headingPath;
		}

		public String relPath() {
			return _relPath;
		}

		public double score() {
			return _score;
		}

		public String text() {
			return _text;
		}

		private final String _chunkId;
		private final List<String> _headingPath;
		private final String _relPath;
		private final double _score;
		private final String _text;

	}

	public static class MetaState {

		public MetaState(String lastIngest, int docCount) {
			_lastIngest = lastIngest;
			_docCount = docCount;
		}

		public int docCount() {
			return _docCount;
		}

		public String lastIngest() {
			return _lastIngest;
		}

		private final int _docCount;
		private final String _lastIngest;

	}

	private synchronized QdrantClient _client() {
		if (_qdrantClient == null) {
			_qdrantClient = new QdrantClient(
				QdrantGrpcClient.newBuilder(
					_host(), 6334, false
				).build());
		}

		return _qdrantClient;
	}

	private String _host() {
		String host = System.getenv("QDRANT_HOST");

		if ((host == null) || host.isEmpty()) {
			return "localhost";
		}

		return host;
	}

	private UUID _metaFileRecordId(String relPath) {
		String name = "semsearch:meta:file:" + relPath;

		return UUID.nameUUIDFromBytes(name.getBytes());
	}

	private UUID _metaStateRecordId() {
		return UUID.nameUUIDFromBytes("semsearch:meta:state".getBytes());
	}

	private int _port() {
		String port = System.getenv("QDRANT_PORT");

		if ((port == null) || port.isEmpty()) {
			return 6333;
		}

		return GetterUtil.getInteger(port, 6333);
	}

	private JsonWithInt.Value _stringValue(String string) {
		return JsonWithInt.Value.newBuilder(
		).setStringValue(
			string
		).build();
	}

	private QdrantClient _qdrantClient;

}
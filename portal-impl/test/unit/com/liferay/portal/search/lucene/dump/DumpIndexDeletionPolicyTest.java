/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.lucene.dump;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Mate Thurzo
 */
public class DumpIndexDeletionPolicyTest {

	@Before
	public void setUp() throws Exception {
		_sourceDirectory = new RAMDirectory();
		_dumpIndexDeletionPolicy = new DumpIndexDeletionPolicy();

		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
			Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));

		indexWriterConfig.setIndexDeletionPolicy(_dumpIndexDeletionPolicy);

		_indexWriter = new IndexWriter(_sourceDirectory, indexWriterConfig);
	}

	@Test
	public void testEmptyDump() throws Exception {
		Directory targetDirectory = _dumpToTargetDirectory(_indexWriter);

		_assertDirectory(_sourceDirectory, targetDirectory);

		_indexWriter.close();
	}

	@Test
	public void testOneCommitDump() throws Exception {
		_addDocument("name", "test1");

		_indexWriter.commit();

		_assertHits(_sourceDirectory, "name", "test1", 1);
		_assertHits(_sourceDirectory, "name", "test2", 0);

		Directory targetDirectory = _dumpToTargetDirectory(_indexWriter);

		_assertDirectory(_sourceDirectory, targetDirectory);

		_assertHits(targetDirectory, "name", "test1", 1);
		_assertHits(targetDirectory, "name", "test2", 0);

		_indexWriter.close();
	}

	@Test
	public void testThreeCommitsDump() throws Exception {
		_addDocument("name", "test1");
		_addDocument("name", "test2");
		_addDocument("name", "test3");

		_assertHits(_sourceDirectory, "name", "test1", 1);
		_assertHits(_sourceDirectory, "name", "test2", 1);
		_assertHits(_sourceDirectory, "name", "test3", 1);
		_assertHits(_sourceDirectory, "name", "test4", 0);

		Directory targetDirectory = _dumpToTargetDirectory(_indexWriter);

		_assertDirectory(_sourceDirectory, targetDirectory);

		_assertHits(targetDirectory, "name", "test1", 1);
		_assertHits(targetDirectory, "name", "test2", 1);
		_assertHits(targetDirectory, "name", "test3", 1);
		_assertHits(targetDirectory, "name", "test4", 0);

		_indexWriter.close();
	}

	@Test
	public void testThreeCommitsOneDeletionDump() throws Exception {
		_addDocument("name", "test1");
		_addDocument("name", "test2");
		_addDocument("name", "test3");

		_deleteDocument("name", "test2");

		_assertHits(_sourceDirectory, "name", "test1", 1);
		_assertHits(_sourceDirectory, "name", "test2", 0);
		_assertHits(_sourceDirectory, "name", "test3", 1);
		_assertHits(_sourceDirectory, "name", "test4", 0);

		Directory targetDirectory = _dumpToTargetDirectory(_indexWriter);

		_assertDirectory(_sourceDirectory, targetDirectory);

		_assertHits(targetDirectory, "name", "test1", 1);
		_assertHits(targetDirectory, "name", "test2", 0);
		_assertHits(targetDirectory, "name", "test3", 1);
		_assertHits(targetDirectory, "name", "test4", 0);

		_indexWriter.close();
	}

	@Test
	public void testThreeCommitsTwoDeletionsDump() throws Exception {
		_addDocument("name", "test1");
		_addDocument("name", "test2");
		_addDocument("name", "test3");

		_deleteDocument("name", "test2");
		_deleteDocument("name", "test3");

		_assertHits(_sourceDirectory, "name", "test1", 1);
		_assertHits(_sourceDirectory, "name", "test2", 0);
		_assertHits(_sourceDirectory, "name", "test3", 0);
		_assertHits(_sourceDirectory, "name", "test4", 0);

		Directory targetDirectory = _dumpToTargetDirectory(_indexWriter);

		_assertDirectory(_sourceDirectory, targetDirectory);

		_assertHits(targetDirectory, "name", "test1", 1);
		_assertHits(targetDirectory, "name", "test2", 0);
		_assertHits(targetDirectory, "name", "test3", 0);
		_assertHits(targetDirectory, "name", "test4", 0);

		_indexWriter.close();
	}

	@Test
	public void testThreeCommitsTwoDeletionsWithOptimizationDump()
		throws Exception {

		_addDocument("name", "test1");
		_addDocument("name", "test2");
		_addDocument("name", "test3");

		_deleteDocument("name", "test2");
		_deleteDocument("name", "test3");

		_indexWriter.commit();

		_assertHits(_sourceDirectory, "name", "test1", 1);
		_assertHits(_sourceDirectory, "name", "test2", 0);
		_assertHits(_sourceDirectory, "name", "test3", 0);
		_assertHits(_sourceDirectory, "name", "test4", 0);

		Directory targetDirectory = _dumpToTargetDirectory(_indexWriter);

		_assertDirectory(_sourceDirectory, targetDirectory);

		_assertHits(targetDirectory, "name", "test1", 1);
		_assertHits(targetDirectory, "name", "test2", 0);
		_assertHits(targetDirectory, "name", "test3", 0);
		_assertHits(targetDirectory, "name", "test4", 0);

		_indexWriter.close();
	}

	@Test
	public void testThreeCommitsWithOptimizationDump() throws Exception {
		_addDocument("name", "test1");
		_addDocument("name", "test2");
		_addDocument("name", "test3");

		_indexWriter.commit();

		_assertHits(_sourceDirectory, "name", "test1", 1);
		_assertHits(_sourceDirectory, "name", "test2", 1);
		_assertHits(_sourceDirectory, "name", "test3", 1);
		_assertHits(_sourceDirectory, "name", "test4", 0);

		Directory targetDirectory = _dumpToTargetDirectory(_indexWriter);

		_assertDirectory(_sourceDirectory, targetDirectory);

		_assertHits(targetDirectory, "name", "test1", 1);
		_assertHits(targetDirectory, "name", "test2", 1);
		_assertHits(targetDirectory, "name", "test3", 1);
		_assertHits(targetDirectory, "name", "test4", 0);

		_indexWriter.close();
	}

	@Test
	public void testTwoCommitsDump() throws Exception {
		_addDocument("name", "test1");
		_addDocument("name", "test2");

		_assertHits(_sourceDirectory, "name", "test1", 1);
		_assertHits(_sourceDirectory, "name", "test2", 1);
		_assertHits(_sourceDirectory, "name", "test3", 0);

		Directory targetDirectory = _dumpToTargetDirectory(_indexWriter);

		_assertDirectory(_sourceDirectory, targetDirectory);

		_assertHits(targetDirectory, "name", "test1", 1);
		_assertHits(targetDirectory, "name", "test2", 1);
		_assertHits(targetDirectory, "name", "test3", 0);

		_indexWriter.close();
	}

	private void _addDocument(String fieldName, String fieldValue)
		throws Exception {

		Document document = new Document();

		Field field = new Field(
			fieldName, fieldValue, Field.Store.YES, Field.Index.ANALYZED);

		document.add(field);

		_indexWriter.addDocument(document);

		_indexWriter.commit();
	}

	private void _assertContent(
			String fileName, IndexInput sourceIndexInput,
			IndexInput targetIndexInput)
		throws Exception {

		for (long i = 0; i < sourceIndexInput.length(); i++) {
			if (sourceIndexInput.readByte() != targetIndexInput.readByte()) {
				Assert.fail(
					fileName +
						" has different source and target byte value at " + i);
			}
		}

		sourceIndexInput.close();
		targetIndexInput.close();
	}

	private void _assertDirectory(
			Directory sourceDirectory, Directory targetDirectory)
		throws Exception {

		String[] sourceFileNames = sourceDirectory.listAll();

		Arrays.sort(sourceFileNames);

		String[] targetFileNames = targetDirectory.listAll();

		Arrays.sort(targetFileNames);

		if (sourceFileNames.length != targetFileNames.length) {
			Assert.fail(
				Arrays.toString(sourceFileNames) +
					" does not have the same length as " +
						Arrays.toString(targetFileNames));
		}

		for (String fileName : sourceFileNames) {
			long sourceLength = sourceDirectory.fileLength(fileName);
			long targetLength = targetDirectory.fileLength(fileName);

			if (sourceLength != targetLength) {
				Assert.fail(
					fileName + " has different source and target lengths");
			}

			_assertContent(
				fileName, sourceDirectory.openInput(fileName),
				targetDirectory.openInput(fileName));
		}
	}

	private void _assertHits(
			Directory directory, String fieldName, String fieldValue,
			int totalHits)
		throws Exception {

		IndexReader indexReader = IndexReader.open(directory);

		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		Term term = new Term(fieldName, fieldValue);

		TermQuery termQuery = new TermQuery(term);

		TopDocs topDocs = indexSearcher.search(termQuery, 1);

		Assert.assertEquals(totalHits, topDocs.totalHits);

		indexSearcher.close();
		indexReader.close();
	}

	private void _deleteDocument(String fieldName, String fieldValue)
		throws Exception {

		Term term = new Term(fieldName, fieldValue);

		_indexWriter.deleteDocuments(term);

		_indexWriter.commit();
	}

	private Directory _dumpToTargetDirectory(IndexWriter indexWriter)
		throws Exception {

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		_dumpIndexDeletionPolicy.dump(
			byteArrayOutputStream, indexWriter, new ReentrantLock());

		byte[] bytes = byteArrayOutputStream.toByteArray();

		Directory targetDirectory = new RAMDirectory();

		IndexCommitSerializationUtil.deserializeIndex(
			new ByteArrayInputStream(bytes), targetDirectory);

		return targetDirectory;
	}

	private DumpIndexDeletionPolicy _dumpIndexDeletionPolicy;
	private IndexWriter _indexWriter;
	private Directory _sourceDirectory;

}
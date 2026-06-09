/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.opensearch._types.ErrorCause;
import org.opensearch.client.opensearch.core.BulkRequest;
import org.opensearch.client.opensearch.core.BulkResponse;
import org.opensearch.client.opensearch.core.bulk.BulkOperation;
import org.opensearch.client.opensearch.core.bulk.BulkResponseItem;
import org.opensearch.client.opensearch.core.bulk.OperationType;

/**
 * @author Michael C. Han
 */
public class BulkDocumentRequestExecutorTest extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_bulkDocumentRequestExecutor = new BulkDocumentRequestExecutor(
			0, openSearchConnectionManager, 0);

		_documentFixture.setUp();
	}

	@Test
	public void testBulkDocumentRequestTranslation() {
		String uid = "1";

		Document document = new DocumentImpl();

		document.addKeyword(Field.TYPE, _MAPPING_NAME);
		document.addKeyword(Field.UID, uid);
		document.addKeyword("staging", "true");

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			TEST_INDEX_NAME, document);

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		bulkDocumentRequest.addBulkableDocumentRequest(indexDocumentRequest);

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			TEST_INDEX_NAME, uid);

		bulkDocumentRequest.addBulkableDocumentRequest(deleteDocumentRequest);

		Document updatedDocument = new DocumentImpl();

		updatedDocument.addKeyword(Field.UID, uid);
		updatedDocument.addKeyword("staging", "false");

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			TEST_INDEX_NAME, uid, updatedDocument);

		bulkDocumentRequest.addBulkableDocumentRequest(updateDocumentRequest);

		BulkRequest bulkRequest =
			_bulkDocumentRequestExecutor.createBulkRequest(bulkDocumentRequest);

		List<BulkOperation> bulkOperations = bulkRequest.operations();

		StringBundler sb = new StringBundler();

		for (BulkOperation bulkOperation : bulkOperations) {
			sb.append(JsonpUtil.toString(bulkOperation));
			sb.append("\n");
		}

		Assert.assertEquals(sb.toString(), 3, bulkOperations.size());
	}

	@Test
	public void testBulkDocumentResponseWithRejectedBulkItems() {
		try {
			_bulkDocumentRequestExecutor.createBulkDocumentResponse(
				_createBulkResponse());

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			Assert.assertEquals(
				"Unable to index 2/3 bulk items: " + _REJECTION_REASON,
				runtimeException.getMessage());
		}
	}

	private BulkResponse _createBulkResponse() {
		ErrorCause errorCause = new ErrorCause.Builder(
		).reason(
			_REJECTION_REASON
		).type(
			"rejected_execution_exception"
		).build();

		BulkResponseItem rejectedBulkResponseItem1 =
			new BulkResponseItem.Builder(
			).error(
				errorCause
			).index(
				TEST_INDEX_NAME
			).operationType(
				OperationType.Index
			).status(
				429
			).build();

		BulkResponseItem rejectedBulkResponseItem2 =
			new BulkResponseItem.Builder(
			).index(
				TEST_INDEX_NAME
			).operationType(
				OperationType.Index
			).status(
				429
			).build();

		BulkResponseItem successfulBulkResponseItem =
			new BulkResponseItem.Builder(
			).index(
				TEST_INDEX_NAME
			).operationType(
				OperationType.Index
			).status(
				200
			).build();

		return new BulkResponse.Builder(
		).errors(
			true
		).items(
			rejectedBulkResponseItem1, rejectedBulkResponseItem2,
			successfulBulkResponseItem
		).took(
			0
		).build();
	}

	private static final String _MAPPING_NAME = "testMapping";

	private static final String _REJECTION_REASON =
		"rejected execution of coordinating operation";

	private BulkDocumentRequestExecutor _bulkDocumentRequestExecutor;
	private final DocumentFixture _documentFixture = new DocumentFixture();

}
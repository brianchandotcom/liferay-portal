/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.model.AddNodeRequest;
import com.liferay.portal.workflow.metrics.model.DeleteNodeRequest;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.util.NodeUtil;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.NodeResource;
import com.liferay.portal.workflow.metrics.rest.spi.resource.SPINodeResource;
import com.liferay.portal.workflow.metrics.search.index.NodeWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/node.properties",
	scope = ServiceScope.PROTOTYPE, service = NodeResource.class
)
public class NodeResourceImpl extends BaseNodeResourceImpl {

	@Override
	public void deleteProcessNode(Long processId, Long nodeId)
		throws Exception {

		DeleteNodeRequest.Builder builder = new DeleteNodeRequest.Builder();

		_nodeWorkflowMetricsIndexer.deleteNode(
			builder.companyId(
				contextCompany.getCompanyId()
			).nodeId(
				nodeId
			).build());
	}

	@Override
	public Page<Node> getProcessNodesPage(Long processId) throws Exception {
		SPINodeResource<Node> spiNodeResource = _getSPINodeResource();

		return spiNodeResource.getProcessNodesPage(processId);
	}

	@Override
	public Node postProcessNode(Long processId, Node node) throws Exception {
		AddNodeRequest.Builder builder = new AddNodeRequest.Builder();

		return NodeUtil.toNode(
			_nodeWorkflowMetricsIndexer.addNode(
				builder.companyId(
					contextCompany.getCompanyId()
				).createDate(
					node.getDateCreated()
				).initial(
					node.getInitial()
				).modifiedDate(
					node.getDateModified()
				).name(
					node.getName()
				).nodeId(
					node.getId()
				).processId(
					processId
				).processVersion(
					node.getProcessVersion()
				).terminal(
					node.getTerminal()
				).type(
					node.getType()
				).build()),
			_language,
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				contextAcceptLanguage.getPreferredLocale(),
				NodeResourceImpl.class));
	}

	private SPINodeResource<Node> _getSPINodeResource() {
		return new SPINodeResource<>(
			contextCompany.getCompanyId(), _indexNameBuilder, _queries,
			_searchRequestExecutor,
			document -> NodeUtil.toNode(
				document, _language,
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					contextAcceptLanguage.getPreferredLocale(),
					NodeResourceImpl.class)));
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Language _language;

	@Reference
	private NodeWorkflowMetricsIndexer _nodeWorkflowMetricsIndexer;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

}
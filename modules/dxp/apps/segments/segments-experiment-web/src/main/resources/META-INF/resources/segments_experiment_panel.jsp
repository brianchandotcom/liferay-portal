<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
SegmentsExperimentDisplayContext segmentsExperimentDisplayContext = (SegmentsExperimentDisplayContext)request.getAttribute(SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_DISPLAY_CONTEXT);
%>


<div class="inline-item my-5 p-5 w-100">
	<span aria-hidden="true" class="loading-animation"></span>
</div>

<react:component
	module="js/SegmentsExperimentApp.es"
	props="<%= segmentsExperimentDisplayContext.getData() %>"
/>
	
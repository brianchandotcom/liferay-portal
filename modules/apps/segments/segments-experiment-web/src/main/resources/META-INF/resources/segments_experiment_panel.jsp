<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/init.jsp" %>

<div id="id-will-be-provided"></div>

<aui:script require='<%= npmResolvedPackageName + "/js/index.es as segmentsExperimentsApp" %>'>
	var context = {
		endpoints: {
			createSegmentsExperimentURL:
				'/segments.segmentsexperiment/add-segments-experiment',
			editSegmentsExperimentsURL:
				'/segments.segmentsexperiment/update-segments-experiment'
		},
		page: {
			classPK: '16',
			classNameId: '20002'
		},
		spritemap: '/o/admin-theme/images/lexicon/icons.svg'
	};

	var segmentsExperiment = {
		segmentsExperimentId: '0',
		name: 'YAY',
		description: 'Cosas fantasticas ocurrirán en este experimento 🧪',
		segmentsExperienceId: '0'
	};

	var props = {
		segmentsExperiences: [
			{
				segmentsExperienceId: '0',
				name: 'Default Experience',
				description: ''
			},
			{
				segmentsExperienceId: '1',
				name: 'Nice People',
				segmentsExperiment: segmentsExperiment
			},
			{
				segmentsExperienceId: '2',
				name: 'Mad People',
				description: ''
			},
			{
				segmentsExperienceId: '3',
				name: 'Misundertood People'
			}
		],
		segmentsExperiment: undefined,
		selectedSegmentsExperienceId: '0'
	}


	segmentsExperimentsApp.default('id-will-be-provided', props, context );
</aui:script>
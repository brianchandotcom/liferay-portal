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

<h3>Image cards</h3>

<%
String DOC_FILE_TITLE = "deliverable.doc";
String MP3_FILE_TITLE = "deliverable.mp3";
String PDF_FILE_TITLE = "deliverable.pdf";
String PNG_FILE_TITLE = "lexicon.icon.camera.png";
String SVG_FILE_TITLE = "lexicon.icon.camera.svg";
%>

<div class="row">
	<div class="col-md-4" id="image-card-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			href="#1"
			imageAlt="thumbnail"
			imageSrc="https://images.unsplash.com/photo-1506976773555-b3da30a63b57"
			subtitle="Author Action"
			title="Madrid"
		/>
	</div>

	<div class="col-md-4" id="image-card-icon-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			icon="camera"
			subtitle="Author Action"
			title="<%= SVG_FILE_TITLE %>"
		/>
	</div>

	<div class="col-md-4" id="image-card-empty-block">
		<clay:image-card actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			subtitle="Author Action"
			title="<%= SVG_FILE_TITLE %>"
		/>
	</div>
</div>

<h4>Image Card with file type</h4>

<div class="row">
	<div class="col-md-4" id="image-card-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="JPG"
			fileTypeStyle="danger"
			href="#1"
			imageAlt="thumbnail"
			imageSrc="https://images.unsplash.com/photo-1499310226026-b9d598980b90"
			subtitle="Author Action"
			title="California"
		/>
	</div>

	<div class="col-md-4" id="image-card-icon-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="SVG"
			fileTypeStyle="warning"
			icon="camera"
			subtitle="Author Action"
			title="<%= SVG_FILE_TITLE %>"
		/>
	</div>

	<div class="col-md-4" id="image-card-empty-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="PNG"
			fileTypeStyle="info"
			subtitle="Author Action"
			title="<%= PNG_FILE_TITLE %>"
		/>
	</div>
</div>

<h4>Image Card with labels</h4>

<div class="row">
	<div class="col-md-4" id="image-card-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="JPG"
			fileTypeStyle="danger"
			href="#1"
			imageAlt="thumbnail"
			imageSrc="https://images.unsplash.com/photo-1503703294279-c83bdf7b4bf4"
			labels="<%= cardsDisplayContext.getLabels() %>"
			subtitle="Author Action"
			title="Beetle"
		/>
	</div>

	<div class="col-md-4" id="image-card-icon-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="SVG"
			fileTypeStyle="warning"
			icon="camera"
			labels="<%= cardsDisplayContext.getLabels() %>"
			labelStylesMap="<%= cardsDisplayContext.getLabelStylesMap() %>"
			subtitle="Author Action"
			title="<%= SVG_FILE_TITLE %>"
		/>
	</div>

	<div class="col-md-4" id="image-card-empty-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="PNG"
			fileTypeStyle="info"
			labels="<%= cardsDisplayContext.getLabels() %>"
			subtitle="Author Action"
			title="<%= SVG_FILE_TITLE %>"
		/>
	</div>
</div>

<h4>Selectable Image Card</h4>

<div class="row">
	<div class="col-md-4" id="image-card-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="JPG"
			fileTypeStyle="danger"
			href="#1"
			imageAlt="thumbnail"
			imageSrc="https://images.unsplash.com/photo-1506020647804-b04ee956dc04"
			labels="<%= cardsDisplayContext.getLabels() %>"
			selectable="<%= true %>"
			selected="<%= true %>"
			subtitle="Author Action"
			title="Beetle"
		/>
	</div>

	<div class="col-md-4" id="image-card-icon-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="SVG"
			fileTypeStyle="warning"
			icon="camera"
			labels="<%= cardsDisplayContext.getLabels() %>"
			selectable="<%= true %>"
			selected="<%= false %>"
			subtitle="Author Action"
			title="<%= SVG_FILE_TITLE %>"
		/>
	</div>

	<div class="col-md-4" id="image-card-empty-block">
		<clay:image-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="PNG"
			fileTypeStyle="info"
			labels="<%= cardsDisplayContext.getLabels() %>"
			selectable="<%= true %>"
			selected="<%= true %>"
			subtitle="Author Action"
			title="<%= SVG_FILE_TITLE %>"
		/>
	</div>
</div>

<h4>File Cards</h4>

<div class="row">
	<div class="col-md-4" id="image-card-block">
		<clay:file-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="PDF"
			fileTypeStyle="danger"
			labels="<%= cardsDisplayContext.getLabels() %>"
			subtitle="Stevie Ray Vaughn"
			title="<%= PDF_FILE_TITLE %>"
		/>
	</div>

	<div class="col-md-4" id="image-card-icon-block">
		<clay:file-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="MP3"
			fileTypeStyle="warning"
			labels="<%= cardsDisplayContext.getLabels() %>"
			labelStylesMap="<%= cardsDisplayContext.getLabelStylesMap() %>"
			selectable="<%= true %>"
			selected="<%= true %>"
			subtitle="Jimi Hendrix"
			title="<%= MP3_FILE_TITLE %>"
		/>
	</div>

	<div class="col-md-4" id="image-card-empty-block">
		<clay:file-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			fileType="DOC"
			fileTypeStyle="info"
			icon="list"
			labels="<%= cardsDisplayContext.getLabels() %>"
			selectable="<%= true %>"
			selected="<%= true %>"
			subtitle="Paco de Lucia"
			title="<%= DOC_FILE_TITLE %>"
		/>
	</div>
</div>

<h4>User Cards</h4>

<div class="row">
	<div class="col-md-6" id="image-card-block">
		<clay:user-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			initials="HS"
			name="User Name"
			subtitle="Latest Action"
			userColor="danger"
		/>
	</div>

	<div class="col-md-6" id="image-card-icon-block">
		<clay:user-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			disabled="<%= true %>"
			imageAlt="thumbnail"
			imageSrc="https://images.unsplash.com/photo-1502290822284-9538ef1f1291"
			name="User name"
			selectable="<%= true %>"
			selected="<%= true %>"
			subtitle="Latest Action"
		/>
	</div>
</div>

<h4>Horizontal Cards</h4>

<div class="row">
	<div class="col-md-6" id="image-card-block">
		<clay:horizontal-card
			title="ReallySuperInsanelyJustIncrediblyLongAndTotallyNotPossibleWordButWeAreReallyTryingToCoverAllOurBasesHereJustInCaseSomeoneIsNutsAsPerUsual"
		/>
	</div>

	<div class="col-md-6" id="image-card-icon-block">
		<clay:horizontal-card
			actionItems="<%= cardsDisplayContext.getDefaultActionItems() %>"
			selectable="<%= true %>"
			selected="<%= true %>"
			title="ReallySuperInsanelyJustIncrediblyLongAndTotallyNotPossibleWordButWeAreReallyTryingToCoverAllOurBasesHereJustInCaseSomeoneIsNutsAsPerUsual"
		/>
	</div>
</div>
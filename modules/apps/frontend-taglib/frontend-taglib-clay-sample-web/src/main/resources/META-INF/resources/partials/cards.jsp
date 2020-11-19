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

<h3>Image Cards</h3>

<%
ClaySampleImageCard claySampleImageCard = new ClaySampleImageCard();
%>

<clay:row>
	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			href="<%= claySampleImageCard.getHref() %>"
			imageAlt="thumbnail"
			imageSrc="https://images.unsplash.com/photo-1506976773555-b3da30a63b57"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="Madrid"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			icon="<%= claySampleImageCard.getIcon() %>"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="<%= _SVG_FILE_TITLE %>"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			subtitle="Author Action"
			title="<%= _SVG_FILE_TITLE %>"
		/>
	</clay:col>
</clay:row>

<h4>Image Card with Sticker</h4>

<clay:row>
	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			href="<%= claySampleImageCard.getHref() %>"
			imageAlt="thumbnail"
			imageSrc="https://images.unsplash.com/photo-1499310226026-b9d598980b90"
			stickerLabel="<%= claySampleImageCard.getStickerLabel() %>"
			stickerStyle="<%= claySampleImageCard.getStickerStyle() %>"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="California"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			icon="<%= claySampleImageCard.getIcon() %>"
			stickerLabel="SVG"
			stickerStyle="<%= claySampleImageCard.getStickerStyle() %>"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="<%= _SVG_FILE_TITLE %>"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			stickerLabel="<%= claySampleImageCard.getStickerLabel() %>"
			stickerStyle="<%= claySampleImageCard.getStickerStyle() %>"
			subtitle="<%= claySampleImageCard.getStickerStyle() %>"
			title="<%= _PNG_FILE_TITLE %>"
		/>
	</clay:col>
</clay:row>

<h4>Image Card with Sticker Shape</h4>

<clay:row>
	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			href="<%= claySampleImageCard.getHref() %>"
			imageAlt="<%= claySampleImageCard.getImageAlt() %>"
			imageSrc="https://images.unsplash.com/photo-1490900245048-1bf948e866c2"
			stickerLabel="<%= claySampleImageCard.getStickerLabel() %>"
			stickerShape="circle"
			stickerStyle="<%= claySampleImageCard.getStickerStyle() %>"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="California"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			icon="<%= claySampleImageCard.getIcon() %>"
			stickerLabel="SVG"
			stickerShape="circle"
			stickerStyle="warning"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="<%= _SVG_FILE_TITLE %>"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			stickerImageAlt="Alt Text"
			stickerImageSrc="https://images.unsplash.com/photo-1502290822284-9538ef1f1291"
			stickerLabel="PNG"
			stickerShape="<%= claySampleImageCard.getStickerShape() %>"
			stickerStyle="info"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="<%= _PNG_FILE_TITLE %>"
		/>
	</clay:col>
</clay:row>

<h4>Image Card with Labels</h4>

<clay:row>
	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			href="<%= claySampleImageCard.getHref() %>"
			imageAlt="thumbnail"
			imageSrc="https://images.unsplash.com/photo-1503703294279-c83bdf7b4bf4"
			labels="<%= claySampleImageCard.getLabels() %>"
			stickerLabel="<% claySampleImageCard.getStickerLabel() %>"
			stickerStyle="danger"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="Beetle"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			icon="camera"
			labels="<%= claySampleImageCard.getLabels() %>"
			labelStylesMap="<%= claySampleImageCard.getLabelStylesMap() %>"
			stickerLabel="SVG"
			stickerStyle="warning"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="<%= _SVG_FILE_TITLE %>"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			labels="<%= claySampleImageCard.getLabels() %>"
			stickerLabel="PNG"
			stickerStyle="<%= claySampleImageCard.getStickerStyle() %>"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="<%= _SVG_FILE_TITLE %>"
		/>
	</clay:col>
</clay:row>

<h4>Selectable Image Card</h4>

<clay:row>
	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			href="<%= claySampleImageCard.getHref() %>"
			imageAlt="thumbnail"
			imageSrc="https://images.unsplash.com/photo-1510360638044-c6f328b5d283"
			labels="<%= claySampleImageCard.getLabels() %>"
			selectable="<%= true %>"
			selected="<%= true %>"
			stickerLabel="<%= claySampleImageCard.getStickerLabel() %>"
			stickerStyle="<%= claySampleImageCard.getStickerStyle() %>"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="Beetle"
		/>
	</clay:col>

	<clay:col
		id="image-card-icon-block"
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			icon="camera"
			labels="<%= claySampleImageCard.getLabels() %>"
			selectable="<%= true %>"
			selected="<%= false %>"
			stickerLabel="SVG"
			stickerStyle="warning"
			subtitle="Author Action"
			title="<%= _SVG_FILE_TITLE %>"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:image-card
			actionDropdownItems="<%= claySampleImageCard.getActionDropdownItems() %>"
			imageSrc="https://images.unsplash.com/photo-1525151212033-377d12acc381"
			labels="<%= claySampleImageCard.getLabels() %>"
			selectable="<%= true %>"
			selected="<%= true %>"
			stickerLabel="PNG"
			stickerStyle="<%= claySampleImageCard.getStickerStyle() %>"
			subtitle="<%= claySampleImageCard.getSubtitle() %>"
			title="<%= _SVG_FILE_TITLE %>"
		/>
	</clay:col>
</clay:row>

<h4>Image Card Using Model</h4>

<clay:row>
	<clay:col
		md="6"
	>

		<%
		claySampleImageCard.setHref("#1");
		claySampleImageCard.setImageSrc("https://images.unsplash.com/photo-1503891450247-ee5f8ec46dc3");
		claySampleImageCard.setSelectable(false);
		claySampleImageCard.setTitle("California");
		%>

		<clay:image-card
			imageCard="<%= claySampleImageCard %>"
		/>
	</clay:col>

	<clay:col
		md="6"
	>

		<%
		claySampleImageCard.setHref("#2");
		claySampleImageCard.setImageSrc("https://images.unsplash.com/photo-1485970671356-ff9156bd4a98");
		claySampleImageCard.setSelected(true);
		claySampleImageCard.setTitle("Mountains");
		%>

		<clay:image-card
			imageCard="<%= claySampleImageCard %>"
		/>
	</clay:col>
</clay:row>

<h4>File Cards</h4>

<%
ClaySampleFileCard claySampleFileCard = new ClaySampleFileCard();
%>

<clay:row>
	<clay:col
		md="4"
	>
		<clay:file-card
			actionDropdownItems="<%= claySampleFileCard.getActionDropdownItems() %>"
			disabled="<%= true %>"
			icon="user"
			labels="<%= claySampleFileCard.getLabels() %>"
			stickerLabel="PDF"
			stickerStyle="<%= claySampleFileCard.getStickerStyle() %>"
			subtitle="<%= claySampleFileCard.getSubtitle() %>"
			title="<%= _PDF_FILE_TITLE %>"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:file-card
			actionDropdownItems="<%= claySampleFileCard.getActionDropdownItems() %>"
			labels="<%= claySampleFileCard.getLabels() %>"
			labelStylesMap="<%= claySampleFileCard.getLabelStylesMap() %>"
			selectable="<%= false %>"
			stickerLabel="MP3"
			stickerStyle="warning"
			subtitle="Jimi Hendrix"
			title="<%= _MP3_FILE_TITLE %>"
		/>
	</clay:col>

	<clay:col
		md="4"
	>
		<clay:file-card
			actionDropdownItems="<%= claySampleFileCard.getActionDropdownItems() %>"
			icon="list"
			labels="<%= claySampleFileCard.getLabels() %>"
			selectable="<%= true %>"
			selected="<%= true %>"
			stickerLabel="<%= claySampleFileCard.getStickerLabel() %>"
			stickerStyle="<%= claySampleFileCard.getStickerStyle() %>"
			subtitle="<%= claySampleFileCard.getSubtitle() %>"
			title="<%= _DOC_FILE_TITLE %>"
		/>
	</clay:col>
</clay:row>

<h4>File Cards Using Model</h4>

<clay:row>
	<clay:col
		md="6"
	>

		<%
		claySampleFileCard.setDisabled(true);
		claySampleFileCard.setStickerLabel("PDF");
		claySampleFileCard.setStickerStyle("info");
		claySampleFileCard.setSubtitle("Another PDF document");
		claySampleFileCard.setTitle(_PDF_FILE_TITLE);
		%>

		<clay:file-card
			fileCard="<%= claySampleFileCard %>"
		/>
	</clay:col>

	<clay:col
		md="6"
	>

		<%
		ClaySampleFileCard sampleFileCard = new ClaySampleFileCard();

		sampleFileCard.setIcon("list");
		sampleFileCard.setSelectable(true);
		sampleFileCard.setSelected(true);
		sampleFileCard.setStickerLabel("MP3");
		sampleFileCard.setStickerStyle("warning");
		sampleFileCard.setSubtitle("More music");
		sampleFileCard.setTitle(_MP3_FILE_TITLE);
		%>

		<clay:file-card
			fileCard="<%= sampleFileCard %>"
		/>
	</clay:col>
</clay:row>

<h4>User Cards</h4>

<%
ClaySampleUserCard claySampleUserCard = new ClaySampleUserCard();
%>

<clay:row>
	<clay:col
		id="image-card-block"
		md="4"
	>
		<clay:user-card
			name="<%= claySampleUserCard.getName() %>"
			selectable="<%= false %>"
			subtitle="<%= claySampleUserCard.getSubtitle() %>"
			userColorClass="<%= claySampleUserCard.getUserColorClass() %>"
		/>
	</clay:col>

	<clay:col
		id="image-card-block"
		md="4"
	>
		<clay:user-card
			actionDropdownItems="<%= claySampleUserCard.getActionDropdownItems() %>"
			icon="picture"
			name="<%= claySampleUserCard.getName() %>"
			selectable="<%= false %>"
			subtitle="<%= claySampleUserCard.getSubtitle() %>"
			userColorClass="danger"
		/>
	</clay:col>

	<clay:col
		id="image-card-icon-block"
		md="4"
	>
		<clay:user-card
			actionDropdownItems="<%= claySampleUserCard.getActionDropdownItems() %>"
			disabled="<%= true %>"
			imageAlt="<%= claySampleUserCard.getImageAlt() %>"
			imageSrc="https://images.unsplash.com/photo-1502290822284-9538ef1f1291"
			name="<%= claySampleUserCard.getName() %>"
			selectable="<%= false %>"
			subtitle="<%= claySampleUserCard.getSubtitle() %>"
		/>
	</clay:col>
</clay:row>

<h4>Selectable User Cards</h4>

<clay:row>
	<clay:col
		id="image-card-block"
		md="4"
	>
		<clay:user-card
			disabled="<%= true %>"
			name="<%= claySampleUserCard.getName() %>"
			subtitle="<%= claySampleUserCard.getSubtitle() %>"
			userColorClass="<%= claySampleUserCard.getUserColorClass() %>"
		/>
	</clay:col>

	<clay:col
		id="image-card-block"
		md="4"
	>
		<clay:user-card
			actionDropdownItems="<%= claySampleUserCard.getActionDropdownItems() %>"
			icon="picture"
			name="<%= claySampleUserCard.getName() %>"
			selected="<%= true %>"
			subtitle="<%= claySampleUserCard.getSubtitle() %>"
			userColorClass="danger"
		/>
	</clay:col>

	<clay:col
		id="image-card-icon-block"
		md="4"
	>
		<clay:user-card
			actionDropdownItems="<%= claySampleUserCard.getActionDropdownItems() %>"
			imageAlt="<%= claySampleUserCard.getImageAlt() %>"
			imageSrc="https://images.unsplash.com/photo-1502290822284-9538ef1f1291"
			name="<%= claySampleUserCard.getName() %>"
			subtitle="<%= claySampleUserCard.getSubtitle() %>"
		/>
	</clay:col>
</clay:row>

<h4>User Cards Using Model</h4>

<clay:row>
	<clay:col
		id="image-card-block"
		md="4"
	>

		<%
		claySampleUserCard.setDisabled(true);
		claySampleUserCard.setSelectable(false);
		%>

		<clay:user-card
			userCard="<%= claySampleUserCard %>"
		/>
	</clay:col>

	<clay:col
		id="image-card-block"
		md="4"
	>

		<%
		claySampleUserCard.setDisabled(false);
		claySampleUserCard.setIcon("picture");
		claySampleUserCard.setUserColorClass("danger");
		%>

		<clay:user-card
			userCard="<%= claySampleUserCard %>"
		/>
	</clay:col>

	<clay:col
		id="image-card-icon-block"
		md="4"
	>

		<%
		claySampleUserCard.setImageSrc("https://images.unsplash.com/photo-1502290822284-9538ef1f1291");
		%>

		<clay:user-card
			userCard="<%= claySampleUserCard %>"
		/>
	</clay:col>
</clay:row>

<h4>Selectable User Cards Using Display Context</h4>

<clay:row>
	<clay:col
		id="image-card-block"
		md="4"
	>

		<%
		claySampleUserCard.setDisabled(true);
		claySampleUserCard.setIcon("user");
		claySampleUserCard.setImageSrc(null);
		claySampleUserCard.setSelectable(true);
		claySampleUserCard.setUserColorClass("info");
		%>

		<clay:user-card
			userCard="<%= claySampleUserCard %>"
		/>
	</clay:col>

	<clay:col
		id="image-card-block"
		md="4"
	>

		<%
		claySampleUserCard.setDisabled(false);
		claySampleUserCard.setIcon("picture");
		claySampleUserCard.setUserColorClass("danger");
		%>

		<clay:user-card
			userCard="<%= claySampleUserCard %>"
		/>
	</clay:col>

	<clay:col
		id="image-card-icon-block"
		md="4"
	>

		<%
		claySampleUserCard.setImageSrc("https://images.unsplash.com/photo-1502290822284-9538ef1f1291");
		%>

		<clay:user-card
			userCard="<%= claySampleUserCard %>"
		/>
	</clay:col>
</clay:row>

<h4>Horizontal Cards</h4>

<clay:row>
	<clay:col
		id="simpleHorizontalCard"
		md="4"
	>
		<clay:horizontal-card
			title="ReallySuperInsanelyJustIncrediblyLongAndTotallyNotPossibleWordButWeAreReallyTryingToCoverAllOurBasesHereJustInCaseSomeoneIsNutsAsPerUsual"
		/>
	</clay:col>

	<clay:col
		id="selectableHorizontalCard"
		md="4"
	>
		<clay:horizontal-card
			actionDropdownItems="<%= cardsDisplayContext.getActionDropdownItems() %>"
			disabled="<%= true %>"
			selectable="<%= true %>"
			title="ReallySuperInsanelyJustIncrediblyLongAndTotallyNotPossibleWordButWeAreReallyTryingToCoverAllOurBasesHereJustInCaseSomeoneIsNutsAsPerUsual"
		/>
	</clay:col>

	<clay:col
		id="modelHorizontalCard"
		md="4"
	>
		<clay:horizontal-card
			horizontalCard="<%= new ClaySampleHorizontalCard() %>"
		/>
	</clay:col>
</clay:row>

<h4>Vertical Cards</h4>

<%
ClaySampleVerticalCard claySampleVerticalCard = new ClaySampleVerticalCard();
%>

<clay:row>
	<clay:col
		md="6"
	>
		<clay:vertical-card
			actionDropdownItems="<%= claySampleVerticalCard.getActionDropdownItems() %>"
			imageAlt="Elephant"
			imageSrc="https://images.unsplash.com/photo-1502290822284-9538ef1f1291"
			labels="<%= claySampleVerticalCard.getLabels() %>"
			selectable="<%= false %>"
			stickerLabel="DXP"
			stickerStyle="info"
			subtitle="<%= claySampleVerticalCard.getSubtitle() %>"
			title="ReallySuperInsanelyJustIncrediblyLongAndTotallyNotPossibleWordButWeAreReallyTryingToCoverAllOurBasesHereJustInCaseSomeoneIsNutsAsPerUsual"
		/>
	</clay:col>

	<clay:col
		md="6"
	>
		<clay:vertical-card
			actionDropdownItems="<%= claySampleVerticalCard.getActionDropdownItems() %>"
			icon="camera"
			labels="<%= claySampleVerticalCard.getLabels() %>"
			selectable="<%= true %>"
			selected="<%= true %>"
			stickerLabel="JPG"
			stickerStyle="<%= claySampleVerticalCard.getStickerStyle() %>"
			subtitle="<%= claySampleVerticalCard.getSubtitle() %>"
			title="ReallySuperInsanelyJustIncrediblyLongAndTotallyNotPossibleWordButWeAreReallyTryingToCoverAllOurBasesHereJustInCaseSomeoneIsNutsAsPerUsual"
		/>
	</clay:col>
</clay:row>

<h4>Vertical Cards Using Model</h4>

<clay:row>
	<clay:col
		md="6"
	>

		<%
		claySampleVerticalCard.setImageSrc("https://images.unsplash.com/photo-1554939437-ecc492c67b78");
		claySampleVerticalCard.setSelectable(true);
		claySampleVerticalCard.setStickerLabel("MAD");
		claySampleVerticalCard.setStickerStyle("success");
		claySampleVerticalCard.setSubtitle("A beautiful city");
		claySampleVerticalCard.setTitle("Madrid");
		%>

		<clay:vertical-card
			verticalCard="<%= claySampleVerticalCard %>"
		/>
	</clay:col>

	<clay:col
		md="6"
	>

		<%
		ClaySampleVerticalCard sampleVerticalCard = new ClaySampleVerticalCard();

		sampleVerticalCard.setDisabled(true);
		sampleVerticalCard.setSelected(true);
		sampleVerticalCard.setStickerStyle("warning");
		sampleVerticalCard.setSubtitle("This card is disabled");
		%>

		<clay:vertical-card
			verticalCard="<%= sampleVerticalCard %>"
		/>
	</clay:col>
</clay:row>

<%!
private static final String _DOC_FILE_TITLE = "deliverable.doc";

private static final String _MP3_FILE_TITLE = "deliverable.mp3";

private static final String _PDF_FILE_TITLE = "deliverable.pdf";

private static final String _PNG_FILE_TITLE = "lexicon.icon.camera.png";

private static final String _SVG_FILE_TITLE = "lexicon.icon.camera.svg";
%>
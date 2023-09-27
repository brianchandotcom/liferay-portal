<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<blockquote>
	<p>Stickers are a visual way to quickly identify content in a different way than badges and labels.</p>
</blockquote>

<h3>SQUARE</h3>

<clay:row
	cssClass="mb-3 text-center"
>
	<clay:col
		md="1"
	>
		<clay:sticker
			label="JPG"
		/>
	</clay:col>

	<clay:col
		md="1"
	>
		<clay:sticker
			icon="picture"
		/>
	</clay:col>
</clay:row>

<h3>ROUND</h3>

<clay:row
	cssClass="mb-3 text-center"
>
	<clay:col
		md="1"
	>
		<clay:sticker
			label="JPG"
			shape="circle"
		/>
	</clay:col>

	<clay:col
		md="1"
	>
		<clay:sticker
			icon="picture"
			shape="circle"
		/>
	</clay:col>
</clay:row>

<h3>POSITION</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col
		md="2"
	>
		<div class="aspect-ratio">
			<img class="aspect-ratio-item-fluid" src="https://clayui.com/images/thumbnail_hot_air_ballon.jpg" />

			<clay:sticker
				displayType="danger"
				label="PDF"
				position="top-left"
			/>
		</div>
	</clay:col>

	<clay:col
		md="2"
	>
		<div class="aspect-ratio">
			<img class="aspect-ratio-item-fluid" src="https://clayui.com/images/thumbnail_hot_air_ballon.jpg" />

			<clay:sticker
				displayType="danger"
				label="PDF"
				position="bottom-left"
			/>
		</div>
	</clay:col>

	<clay:col
		md="2"
	>
		<div class="aspect-ratio">
			<img class="aspect-ratio-item-fluid" src="https://clayui.com/images/thumbnail_hot_air_ballon.jpg" />

			<clay:sticker
				displayType="danger"
				label="PDF"
				position="top-right"
			/>
		</div>
	</clay:col>

	<clay:col
		md="2"
	>
		<div class="aspect-ratio">
			<img class="aspect-ratio-item-fluid" src="https://clayui.com/images/thumbnail_hot_air_ballon.jpg" />

			<clay:sticker
				displayType="danger"
				label="PDF"
				position="bottom-right"
			/>
		</div>
	</clay:col>
</clay:row>
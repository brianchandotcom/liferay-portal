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
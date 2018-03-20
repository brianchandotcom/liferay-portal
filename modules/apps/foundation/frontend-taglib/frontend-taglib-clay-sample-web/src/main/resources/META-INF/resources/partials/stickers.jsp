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

<blockquote><p>Stickers are a visual way to quickly identify content in a different way than badges and labels.</p></blockquote>

<h3>SQUARE</h3>

<div class="mb-3 row text-center">
	<div class="col-md-1">
		<clay:sticker
			label="JPG"
		/>
	</div>

	<div class="col-md-1">
		<clay:sticker
			icon="picture"
		/>
	</div>
</div>

<h3>ROUND</h3>

<div class="mb-3 row text-center">
	<div class="col-md-1">
		<clay:sticker
			label="JPG"
			shape="circle"
		/>
	</div>

	<div class="col-md-1">
		<clay:sticker
			icon="picture"
			shape="circle"
		/>
	</div>
</div>

<h3>POSITION</h3>

<div class="mb-3 row">
	<div class="col-md-2">
		<div class="aspect-ratio">
			<img class="aspect-ratio-item-fluid" src="https://claycss.com/images/thumbnail_hot_air_ballon.jpg" />

			<clay:sticker
				label="PDF"
				position="top-left"
				style="danger"
			/>
		</div>
	</div>

	<div class="col-md-2">
		<div class="aspect-ratio">
			<img class="aspect-ratio-item-fluid" src="https://claycss.com/images/thumbnail_hot_air_ballon.jpg" />

			<clay:sticker
				label="PDF"
				position="bottom-left"
				style="danger"
			/>
		</div>
	</div>

	<div class="col-md-2">
		<div class="aspect-ratio">
			<img class="aspect-ratio-item-fluid" src="https://claycss.com/images/thumbnail_hot_air_ballon.jpg" />

			<clay:sticker
				label="PDF"
				position="top-right"
				style="danger"
			/>
		</div>
	</div>

	<div class="col-md-2">
		<div class="aspect-ratio">
			<img class="aspect-ratio-item-fluid" src="https://claycss.com/images/thumbnail_hot_air_ballon.jpg" />

			<clay:sticker
				label="PDF"
				position="bottom-right"
				style="danger"
			/>
		</div>
	</div>
</div>
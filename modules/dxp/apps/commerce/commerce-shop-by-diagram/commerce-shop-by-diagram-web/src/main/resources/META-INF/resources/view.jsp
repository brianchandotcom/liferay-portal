<%@ page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%--
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

<%--<%--%>


<%--JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();--%>

<%--%>--%>

<div id="shop-by-diagram" />

	<aui:script require="commerce-shop-by-diagram-web/js/Diagram as Diagram">
		Diagram.default('shop-by-diagram', 'shop-by-diagram', {
			enablePanZoom: true,
				enableResetZoom: true,
			image:
			'https://i0.wp.com/detoxicrecenze.com/wp-content/uploads/2018/05/straight-6-engine-diagram-460-ford-engine-diagram-wiring-info-e280a2-of-straight-6-engine-diagram.jpg',
				imageSettings: {
			height: '500px',
				width: '100%',
			},
			navigationController: {
				dragStep: 10,
					enable: true,
					enableDrag: true,
					position: {
					bottom: '15px',
						left: '',
						right: '50px',
						top: '',
				},
			},
			pins: [
				{
					cx: 150,
					cy: 50,
					draggable: true,
					fill: '#f90e19',
					id: 0,
					label: 'zero',
					linked_to_sku: 'sku',
					quantity: 0,
					r: 25,
					sku: '0bskoi0o',
				},
				{
					cx: 353.6965532023335,
					cy: 36.92566831583167,
					draggable: true,
					fill: '#b6fb00',
					id: 1,
					label: 'test1',
					linked_to_sku: 'sku',
					quantity: 11,
					r: 10,
					sku: '1nonlo',
				},
				{
					cx: 100.38075900390436,
					cy: 404.9651880080625,
					draggable: true,
					fill: '#733edc',
					id: 2,
					label: 'test2',
					linked_to_sku: 'sku',
					quantity: 2,
					r: 21,
					sku: '2npiomnn',
				},
				{
					cx: 225.42119508877246,
					cy: 208.01679515257248,
					draggable: true,
					fill: '#34fbe8',
					id: 3,
					label: 'assT',
					linked_to_sku: 'sku',
					quantity: 0,
					r: 18,
					sku: '3nionmoiv3',
				},
				{
					cx: 284.1028161914142,
					cy: 179.06395973801364,
					draggable: true,
					fill: '#dc7aa5',
					id: 4,
					label: 'tw21',
					linked_to_sku: 'sku',
					quantity: 33,
					r: 12,
					sku: '4n4oin',
				},
				{
					cx: 51.23146641116725,
					cy: 130.62750589926333,
					draggable: true,
					fill: '#2c3d34',
					id: 5,
					label: '5',
					linked_to_sku: 'sku',
					quantity: 10,
					r: 23,
					sku: 'fmwfo5in',
				},
				{
					cx: 469.6902055639296,
					cy: 111.82067084583896,
					draggable: true,
					fill: '#96e135',
					id: 6,
					label: '6',
					linked_to_sku: 'sku',
					quantity: 6,
					r: 16,
					sku: '6nkljnwsd6',
				},
				{
					cx: 343.7363091925929,
					cy: 151.74109656223635,
					draggable: true,
					fill: '#ff7f0e',
					id: 7,
					label: '78',
					linked_to_sku: 'sku',
					quantity: 3,
					r: 18,
					sku: 'no7i7n',
				},
				{
					cx: 463.6874080642692,
					cy: 393.84202391797453,
					draggable: true,
					fill: '#c60f0c',
					id: 8,
					label: '8',
					linked_to_sku: 'sku',
					quantity: 1,
					r: 13,
					sku: '',
				},
				{
					cx: 139.69729329671517,
					cy: 195.0193289694761,
					draggable: true,
					fill: '#ad9520',
					id: 9,
					label: 'tt9',
					linked_to_sku: 'sku',
					quantity: 0,
					r: 25,
					sku: '999999jjujn',
				},
			],
			spritemap: './assets/clay/icons.svg',
			zoomController: {
				enable: true,
					position: {
					bottom: '0px',
						left: '',
						right: '200px',
						top: '',
				}
			}
		});
	</aui:script>

<%--	<react:component--%>
<%--		module='js/Diagram'--%>
<%--		props='<%=--%>
<%--			HashMapBuilder.<String, Object>put(--%>
<%--				"enablePanZoom", true--%>
<%--			).put(--%>

<%--			).build()--%>
<%--		%>'--%>
<%--	/>--%>

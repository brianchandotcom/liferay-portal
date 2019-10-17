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

AUI.add(
	'liferay-dd-proxy',
	A => {
		var body = A.getBody();

		var DDM = A.DD.DDM;

		A.mix(
			DDM,
			{
				_createFrame() {
					if (!DDM._proxy) {
						DDM._proxy = true;

						var proxyNode = A.Node.create('<div></div>');

						proxyNode.setStyles({
							display: 'none',
							left: '-999px',
							position: 'absolute',
							top: '-999px',
							zIndex: '999'
						});

						body.prepend(proxyNode);

						proxyNode.set('id', A.guid());

						proxyNode.addClass(DDM.CSS_PREFIX + '-proxy');

						DDM._proxy = proxyNode;
					}
				},

				_setFrame(drag) {
					var activeHandle;

					var cursor = 'auto';

					var dragNode = drag.get('dragNode');
					var node = drag.get('node');

					activeHandle = DDM.activeDrag.get('activeHandle');

					if (activeHandle) {
						cursor = activeHandle.getStyle('cursor');
					}

					if (cursor === 'auto') {
						cursor = DDM.get('dragCursor');
					}

					dragNode.setStyles({
						border: drag.proxy.get('borderStyle'),
						cursor,
						display: 'block',
						visibility: 'hidden'
					});

					if (drag.proxy.get('cloneNode')) {
						dragNode = drag.proxy.clone();
					}

					if (drag.proxy.get('resizeFrame')) {
						var size = node.invoke('getBoundingClientRect');

						dragNode.setStyles({
							height: Math.ceil(size.height),
							width: Math.ceil(size.width)
						});
					}

					if (drag.proxy.get('positionProxy')) {
						dragNode.setXY(drag.nodeXY);
					}

					dragNode.setStyle('visibility', 'visible');
				}
			},
			true
		);
	},
	'',
	{
		requires: ['dd-proxy']
	}
);

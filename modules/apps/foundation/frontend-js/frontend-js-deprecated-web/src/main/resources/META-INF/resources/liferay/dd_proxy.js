AUI.add(
	'liferay-dd-proxy',
	function(A) {
		var body = A.getBody();

		var DDM = A.DD.DDM;

		A.mix(
			DDM,
			{
				_createFrame: function() {
					if (!DDM._proxy) {
						DDM._proxy = true;

						var proxyNode = A.Node.create('<div></div>');

						proxyNode.setStyles(
							{
								display: 'none',
								left: '-999px',
								position: 'absolute',
								top: '-999px',
								zIndex: '999'
							}
						);

						body.prepend(proxyNode);

						proxyNode.set('id', A.guid());

						proxyNode.addClass(DDM.CSS_PREFIX + '-proxy');

						DDM._proxy = proxyNode;
					}
				},

				_setFrame: function(drag) {
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

					dragNode.setStyles(
						{
							border: drag.proxy.get('borderStyle'),
							cursor: cursor,
							display: 'block',
							visibility: 'hidden'
						}
					);

					if (drag.proxy.get('cloneNode')) {
						dragNode = drag.proxy.clone();
					}

					if (drag.proxy.get('resizeFrame')) {
						var size = node.invoke('getBoundingClientRect');

						dragNode.setStyles(
							{
								height: Math.ceil(size.height),
								width: Math.ceil(size.width)
							}
						);
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
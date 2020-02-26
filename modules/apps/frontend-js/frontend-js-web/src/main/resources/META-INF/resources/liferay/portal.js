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

(function(A, Liferay) {
	var ToolTip = Liferay.namespace('Portal.ToolTip');

	var BODY_CONTENT = 'bodyContent';

	var TRIGGER = 'trigger';

	ToolTip._getText = function(id) {
		var node = A.one('#' + id);

		var text = '';

		if (node) {
			var toolTipTextNode = node.next('.taglib-text');

			if (toolTipTextNode) {
				text = toolTipTextNode.html();
			}
		}

		return text;
	};

	ToolTip.hide = function() {
		var instance = this;

		var cached = instance._cached;

		if (cached) {
			cached.hide();
		}
	};

	Liferay.provide(
		ToolTip,
		'show',
		function(obj, text, tooltipConfig) {
			var instance = this;

			var cached = instance._cached;
			var hideTooltipTask = instance._hideTooltipTask;

			if (!cached) {
				var config = A.merge(
					{
						cssClass: 'tooltip-help',
						html: true,
						opacity: 1,
						stickDuration: 100,
						visible: false,
						zIndex: Liferay.zIndex.TOOLTIP,
					},
					tooltipConfig
				);

				cached = new A.Tooltip(config).render();

				cached.after(
					'visibleChange',
					A.bind('_syncUIPosAlign', cached)
				);

				hideTooltipTask = A.debounce(
					'_onBoundingBoxMouseleave',
					cached.get('stickDuration'),
					cached
				);

				instance._hideTooltipTask = hideTooltipTask;

				instance._cached = cached;
			}
			else {
				cached.setAttrs(tooltipConfig);
			}

			hideTooltipTask.cancel();

			if (obj.jquery) {
				obj = obj[0];
			}

			obj = A.one(obj);

			if (text == null) {
				text = instance._getText(obj.guid());
			}

			cached.set(BODY_CONTENT, text);
			cached.set(TRIGGER, obj);

			var boundingBox = cached.get('boundingBox');

			boundingBox.detach('hover');
			obj.detach('hover');

			obj.on(
				'hover',
				A.bind('_onBoundingBoxMouseenter', cached),
				hideTooltipTask
			);

			boundingBox.on(
				'hover',
				() => {
					hideTooltipTask.cancel();

					obj.once('mouseenter', hideTooltipTask.cancel);
				},
				hideTooltipTask
			);

			cached.show();
		},
		['aui-tooltip-base']
	);
})(AUI(), Liferay);

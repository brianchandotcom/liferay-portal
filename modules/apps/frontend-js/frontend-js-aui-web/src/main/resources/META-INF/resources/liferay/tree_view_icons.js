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
	'liferay-tree-view-icons',
	function(A) {
		var ICON_DEPRECATED_CLASSES = [
			'glyphicon',
			'glyphicon-check',
			'glyphicon-file',
			'glyphicon-folder',
			'glyphicon-folder-close',
			'glyphicon-folder-open',
			'glyphicon-minus',
			'glyphicon-plus',
			'icon-file',
			'icon-minus',
			'icon-plus'
		];

		var clearIconClasses = function(element) {
			ICON_DEPRECATED_CLASSES.forEach(className =>
				element.removeClass(className)
			);
		};

		var originalSyncIconUIFn = A.TreeNode.prototype._syncIconUI;

		A.TreeNode.prototype._syncIconUI = function(args) {
			originalSyncIconUIFn.call(this, args);

			var hasChildren = this.childrenLength > 0;
			var expanded = this.get('expanded');
			var hitAreaEl = this.get('hitAreaEl');
			var iconEl = this.get('iconEl');

			var hitAreaContent = hasChildren
				? Liferay.Util.getLexiconIconTpl(expanded ? 'hr' : 'plus')
				: '<span class="tree-hitarea"></span>';

			hitAreaEl.setHTML(hitAreaContent);

			clearIconClasses(hitAreaEl);

			var expandedIcon = expanded ? 'move-folder' : 'folder';
			var icon = hasChildren ? expandedIcon : 'page';

			iconEl.setHTML(Liferay.Util.getLexiconIconTpl(icon));

			clearIconClasses(iconEl);
		};

		var originalSyncIconCheckUIFn =
			A.TreeNodeCheck.prototype._syncIconCheckUI;

		A.TreeNodeCheck.prototype._syncIconCheckUI = function(args) {
			originalSyncIconCheckUIFn.call(this, args);

			var checked = this.isChecked();
			var checkContainerEl = this.get('checkContainerEl');

			checkContainerEl.setHTML(
				Liferay.Util.getLexiconIconTpl(
					checked ? 'check-square' : 'square-hole'
				)
			);

			clearIconClasses(checkContainerEl);
		};
	},
	'',
	{
		requires: ['aui-tree-view']
	}
);

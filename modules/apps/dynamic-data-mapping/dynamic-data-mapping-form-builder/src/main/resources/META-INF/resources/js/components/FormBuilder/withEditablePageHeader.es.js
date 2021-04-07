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

import {PagesVisitor} from 'data-engine-js-components-web';
import Component from 'metal-jsx';

import {sub} from '../../util/strings.es';
import formBuilderProps from './props.es';

const withEditablePageHeader = (ChildComponent) => {
	class EditablePageHeader extends Component {
		getPages() {
			const {pages} = this.props;
			const total = pages.length;
			const visitor = new PagesVisitor(pages);
			let lastPageIndex = total;

			if (pages[pages.length - 1].contentRenderer == 'success') {
				lastPageIndex = total - 1;
			}

			return visitor.mapPages((page, pageIndex) => {
				return {
					...page,
					headerRenderer: 'editable',
					pageIndex,
					pagination: sub(Liferay.Language.get('page-x-of-x'), [
						pageIndex + 1,
						lastPageIndex,
					]),
					placeholder: Liferay.Language.get('page-title'),
					total,
				};
			});
		}

		render() {
			return (
				<div>
					<ChildComponent {...this.props} pages={this.getPages()} />
				</div>
			);
		}
	}

	EditablePageHeader.PROPS = {
		...formBuilderProps,
	};

	return EditablePageHeader;
};

export default withEditablePageHeader;

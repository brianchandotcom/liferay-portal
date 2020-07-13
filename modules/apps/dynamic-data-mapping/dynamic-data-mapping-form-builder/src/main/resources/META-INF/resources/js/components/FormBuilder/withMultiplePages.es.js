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

import Component from 'metal-jsx';
import {Config} from 'metal-state';

import formBuilderProps from './props.es';

const withMultiplePages = (ChildComponent) => {
	class MultiplePages extends Component {
		getPages() {
			let {pages} = this.props;
			const {
				defaultLanguageId,
				paginationMode,
				successPageSettings,
			} = this.props;

			if (successPageSettings.enabled) {
				pages = [
					...pages,
					{
						contentRenderer: 'success',
						defaultLanguageId,
						paginationItemRenderer: `${paginationMode}_success`,
						rows: [],
						successPageSettings,
					},
				];
			}

			return pages.map((page) => {
				return {
					...page,
					enabled: true,
				};
			});
		}

		getPaginationPosition() {
			const {pages, paginationMode} = this.props;
			const position = paginationMode === 'wizard' ? 'top' : 'bottom';

			return pages.length > 1 ? position : 'top';
		}

		render() {
			return (
				<div
					class={`container ddm-paginated-builder ${this.getPaginationPosition()}`}
				>
					<ChildComponent {...this.props} pages={this.getPages()} />
				</div>
			);
		}
	}

	MultiplePages.PROPS = {

		/**
		 * @instance
		 * @memberof LayoutProvider
		 * @type {boolean}
		 */

		allowSuccessPage: Config.bool().value(true),

		...formBuilderProps,
	};

	MultiplePages.STATE = {

		/**
		 * @default false
		 * @instance
		 * @memberof FormRenderer
		 * @type {boolean}
		 */

		dropdownExpanded: Config.bool().value(false).internal(),
	};

	return MultiplePages;
};

export default withMultiplePages;

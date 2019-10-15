/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {Component} from 'react';
import {getPluralMessage} from '../../utils/language.es';
import {PropTypes} from 'prop-types';

class ItemDropdown extends Component {
	static propTypes = {
		hidden: PropTypes.bool,
		itemCount: PropTypes.number,
		onClickHide: PropTypes.func,
		onClickPin: PropTypes.func,
		pinned: PropTypes.bool
	};

	static defaultProps = {
		itemCount: 1
	};

	state = {
		show: false
	};

	_handleDropdownAction = actionFn => event => {
		event.preventDefault();

		actionFn(event);

		this.setState({show: false});
	};

	_handleSetShow = value => {
		this.setState({show: value});
	};

	render() {
		const {
			hidden,
			itemCount,
			onClickHide,
			onClickPin,
			pinned,
			...otherProps
		} = this.props;

		const {show} = this.state;

		return (
			<ClayDropDown
				active={show}
				hasLeftSymbols
				onActiveChange={this._handleSetShow}
				trigger={
					<ClayButton
						aria-expanded="false"
						aria-haspopup="true"
						className="btn-outline-borderless component-action"
						title={Liferay.Language.get('toggle-dropdown')}
					>
						<ClayIcon symbol="ellipsis-v" />
					</ClayButton>
				}
				{...otherProps}
			>
				<ClayDropDown.ItemList>
					{onClickPin && (
						<ClayDropDown.Item
							key={pinned ? 'UNPIN' : 'PIN'}
							onClick={this._handleDropdownAction(onClickPin)}
							symbolLeft={pinned ? 'unpin' : 'pin'}
						>
							{pinned
								? getPluralMessage(
										Liferay.Language.get('unpin-result'),
										Liferay.Language.get('unpin-results'),
										itemCount
								  )
								: getPluralMessage(
										Liferay.Language.get('pin-result'),
										Liferay.Language.get('pin-results'),
										itemCount
								  )}
						</ClayDropDown.Item>
					)}
					{onClickHide && (
						<ClayDropDown.Item
							onClick={this._handleDropdownAction(onClickHide)}
							symbolLeft={hidden ? 'view' : 'hidden'}
						>
							{hidden
								? getPluralMessage(
										Liferay.Language.get('show-result'),
										Liferay.Language.get('show-results'),
										itemCount
								  )
								: getPluralMessage(
										Liferay.Language.get('hide-result'),
										Liferay.Language.get('hide-results'),
										itemCount
								  )}
						</ClayDropDown.Item>
					)}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		);
	}
}

export default ItemDropdown;

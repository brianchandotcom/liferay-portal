/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {PropTypes} from 'prop-types';
import React, {Component} from 'react';

import {getPluralMessage} from '../../utils/language.es';

class ItemDropdown extends Component {
	static propTypes = {
		hidden: PropTypes.bool,
		itemCount: PropTypes.number,
		onClickHide: PropTypes.func,
		onClickPin: PropTypes.func,
		pinned: PropTypes.bool,
	};

	static defaultProps = {
		itemCount: 1,
	};

	state = {
		show: false,
	};

	_handleDropdownAction = (actionFn) => (event) => {
		event.preventDefault();

		actionFn(event);

		this.setState({show: false});
	};

	_handleSetShow = (value) => {
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
						aria-label={Liferay.Language.get('actions')}
						className="btn-outline-borderless component-action"
						title={Liferay.Language.get('actions')}
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

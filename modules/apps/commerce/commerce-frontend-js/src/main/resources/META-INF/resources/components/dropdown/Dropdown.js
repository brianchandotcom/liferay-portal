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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {OPEN_MODAL} from '../../utilities/eventsDefinitions';
import {getRandomId, sortByKey} from '../../utilities/index';
import {resolveModalSize} from '../../utilities/modals/index';
import Modal from '../modal/Modal';

function Dropdown(props) {
	const [active, setActive] = useState(false);

	const [dropdownSupportModalId] = useState('support-modal-' + getRandomId());

	function handleAction({onClick, target = 'link', title, url}) {
		if (target.includes('modal')) {
			Liferay.fire(OPEN_MODAL, {
				closeOnSubmit: true,
				id: dropdownSupportModalId,
				size: resolveModalSize(target),
				title,
				url,
			});
		}

		if (onClick) {
			/* eslint-disable-next-line no-eval */
			eval(onClick);
		}
	}

	if (!props.items || !props.items.length) {
		return null;
	}

	const sortedItems = sortByKey(props.items, 'order');

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="component-action dropdown-toggle"
					displayType="unstyled"
				>
					<ClayIcon spritemap={props.spritemap} symbol="ellipsis-v" />
				</ClayButton>
			}
		>
			<Modal id={dropdownSupportModalId} />
			<ClayDropDown.ItemList>
				<ClayDropDown.Group>
					{sortedItems.map((item, i) => {
						const dropdownProps =
							item.target === 'modal' || item.onClick
								? {
										onClick: (event) => {
											event.preventDefault();
											setActive(false);

											return handleAction({
												onClick: item.onClick,
												target: item.target,
												title: item.title,
												url: item.href,
											});
										},
								  }
								: {
										'data-senna-off': true,
										'href': item.href,
								  };

						return (
							<ClayDropDown.Item key={i} {...dropdownProps}>
								{item.icon && (
									<span className="pr-2">
										<ClayIcon
											spritemap={props.spritemap}
											symbol={item.icon}
										/>
									</span>
								)}
								{item.label}
							</ClayDropDown.Item>
						);
					})}
				</ClayDropDown.Group>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

Dropdown.propTypes = {
	items: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string.isRequired,
			icon: PropTypes.string,
			label: PropTypes.string.isRequired,
			order: PropTypes.number,
			target: PropTypes.oneOf(['link', 'modal']),
		})
	),
	spritemap: PropTypes.string.isRequired,
};

export default Dropdown;

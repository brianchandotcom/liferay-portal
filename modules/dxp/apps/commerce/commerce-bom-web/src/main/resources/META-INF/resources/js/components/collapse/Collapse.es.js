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

import React, {useEffect, useRef, useState} from 'react';

export default function Dropdown(props) {
	const [state, setState] = useState(props.open ? 'expanded' : 'collapsed');
	const bodyRef = useRef(null);

	function toggle() {
		switch (true) {
			case state === 'expanded':
				setState('collapsing');
				bodyRef.current.style.maxHeight =
					bodyRef.current.scrollHeight + 'px';
				bodyRef.current.addEventListener(
					'transitionend',
					() => setState('collapsed'),
					{once: true}
				);
				break;
			case state === 'collapsed':
				setState('expanding');
				bodyRef.current.style.maxHeight = '0px';
				bodyRef.current.addEventListener(
					'transitionend',
					() => setState('expanded'),
					{once: true}
				);
				break;
			default:
				break;
		}
	}

	useEffect(() => {
		switch (true) {
			case state === 'expanding':
				bodyRef.current.style.maxHeight =
					bodyRef.current.scrollHeight + 'px';
				break;
			case state === 'expanded':
				bodyRef.current.style.maxHeight = '';
				break;
			case state === 'collapsing':
				bodyRef.current.style.maxHeight = '0px';
				break;
			case state === 'collapsed':
				bodyRef.current.style.maxHeight = '';
				break;
			default:
				break;
		}
	}, [state]);

	return (
		<div className="commerce-collapse panel">
			<button
				aria-controls="collapseTwo"
				aria-expanded={state === 'expanded'}
				className={`commerce-collapse__header commerce-collapse__header--${state}`}
				onClick={toggle}
				role="tab"
				type="button"
			>
				<span className="collapse-title">{props.title}</span>
				<span className="collapse-icon-closed">{props.closedIcon}</span>
				<span className="collapse-icon-open">{props.openIcon}</span>
			</button>
			<div
				className={`commerce-collapse__content commerce-collapse__content--${state}`}
				ref={bodyRef}
				role="tabpanel"
			>
				{props.content}
			</div>
		</div>
	);
}

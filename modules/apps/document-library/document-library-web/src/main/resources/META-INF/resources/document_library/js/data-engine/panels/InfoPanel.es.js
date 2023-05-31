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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch, runScriptsInElement} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import '../../../css/data_engine_info_panel.scss';

export default function InfoPanel({title, url}) {
	const [loading, setLoading] = useState(true);
	const [content, setContent] = useState('');
	const isMounted = useIsMounted();
	const closeButtonMessage = () => {
		if (title === 'Details') {
			return Liferay.Language.get('close-details-panel');
		}
		else if (title === 'Permissions') {
			return Liferay.Language.get('close-permissions-panel');
		}
		else if (title === 'Additional Metadata Fields') {
			return Liferay.Language.get(
				'close-additional-metadata-fields-panel'
			);
		}
	};

	useEffect(() => {
		fetch(url)
			.then((response) => response.text())
			.then((content) => {
				if (isMounted()) {
					setContent(content);
					setLoading(false);
				}
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	}, [isMounted, url]);

	return (
		<div className="dm-sidebar sidebar-sm">
			<div className="sidebar-header">
				<div className="autofit-row mb-3 sidebar-section">
					<div className="component-title">
						<h2 className="text-truncate-inline">{title}</h2>
					</div>

					<ClayButtonWithIcon
						aria-label={closeButtonMessage()}
						displayType="unstyled"
						onClick={() => {
							const builder = document.querySelector(
								'.ddm-form-builder--sidebar-open'
							);
							const sidebar = document.querySelector(
								'.multi-panel-sidebar-content-open'
							);

							builder.classList.remove(
								'ddm-form-builder--sidebar-open'
							);
							sidebar.classList.remove(
								'multi-panel-sidebar-content-open'
							);

							const tab = document.querySelector(
								`[data-panel-id="${title}"]`
							);
							tab?.focus();
							tab.ariaSelected = false;
						}}
						size="sm"
						symbol="times"
						tabIndex="0"
						title={Liferay.Language.get('close')}
					/>
				</div>
			</div>

			<div className="sidebar-body">
				{loading ? (
					<ClayLoadingIndicator />
				) : (
					<InfoPanelBody content={content} />
				)}
			</div>
		</div>
	);
}

class InfoPanelBody extends React.Component {
	constructor(props) {
		super(props);

		this._ref = React.createRef();
	}

	componentDidMount() {
		if (this._ref.current) {
			runScriptsInElement(this._ref.current);

			this._ref.current.addEventListener('change', this._handleOnChange);
		}
	}
	shouldComponentUpdate() {
		return false;
	}

	render() {
		return (
			<div
				dangerouslySetInnerHTML={{__html: this.props.content}}
				ref={this._ref}
			/>
		);
	}
}

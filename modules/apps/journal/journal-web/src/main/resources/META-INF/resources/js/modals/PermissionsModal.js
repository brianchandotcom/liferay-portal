/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch, runScriptsInElement} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export default function PermissionsModal({
	actionButton,
	onCloseModal,
	onPublishButtonClick,
	permissionsURL,
	portletNamespace,
}) {
	const form = `${portletNamespace}fm1`;

	const {observer, onClose} = useModal({
		onClose: () => {
			onCloseModal();
		},
	});
	const [loading, setLoading] = useState(true);
	const [content, setContent] = useState('');
	const isMounted = useIsMounted();

	useEffect(() => {
		fetch(permissionsURL)
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
	}, [isMounted, permissionsURL]);

	return (
		<ClayModal className="m-0" observer={observer} size="lg">
			<ClayModal.Header>
				{actionButton === 'publish'
					? Liferay.Language.get('publish-web-content')
					: Liferay.Language.get('save-as-draft')}
			</ClayModal.Header>

			<ClayModal.Body className="m-0">
				<p className="text-secondary">
					{actionButton === 'publish'
						? Liferay.Language.get(
								'confirm-the-web-content-visibility-before-publishing'
						  )
						: Liferay.Language.get(
								'confirm-the-web-content-visibility-before-saving-as-draft'
						  )}
				</p>

				{loading ? (
					<ClayLoadingIndicator />
				) : (
					<PermissionsModalBody content={content} form={form} />
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => {
								onClose();
							}}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							form={form}
							onClick={onPublishButtonClick}
							type="submit"
						>
							{actionButton === 'publish'
								? Liferay.Language.get('publish')
								: Liferay.Language.get('save-as-draft')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

class PermissionsModalBody extends React.Component {
	constructor(props) {
		super(props);

		this._ref = React.createRef();
	}

	componentDidMount() {
		if (this._ref.current) {
			runScriptsInElement(this._ref.current);

			const inputs = this._ref.current.querySelectorAll('input');

			inputs.forEach((input) => {
				input.setAttribute('form', this.props.form);
			});
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

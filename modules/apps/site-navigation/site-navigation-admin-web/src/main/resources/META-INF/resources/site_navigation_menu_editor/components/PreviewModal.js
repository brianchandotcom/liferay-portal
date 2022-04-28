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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {addParams} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

import {useConstants} from '../contexts/ConstantsContext';

export function PreviewModal({observer}) {
	const {
		displayTemplateOptions,
		portletNamespace,
		previewSiteNavigationMenuURL,
	} = useConstants();

	const displayTemplateSelectId = `${portletNamespace}-displayTemplateSelect`;

	const [displayTemplateId, setDisplayTemplateId] = useState(
		displayTemplateOptions.find((option) => option.selected).value
	);

	const [loading, setLoading] = useState(true);

	const iframeRef = useRef();

	const previewURL = addParams(
		{
			[`${portletNamespace}ddmTemplateKey`]: displayTemplateId,
		},
		previewSiteNavigationMenuURL
	);

	return (
		<ClayModal observer={observer} size="xl">
			<ClayModal.Header>
				{Liferay.Language.get('preview-menu')}
			</ClayModal.Header>

			<ClayModal.Body className="site-navigation__preview-modal">
				<ClayForm.Group className="h-100">
					<label htmlFor={displayTemplateSelectId}>
						{Liferay.Language.get('display-template')}
					</label>

					<ClaySelectWithOption
						id={displayTemplateSelectId}
						onChange={(event) => {
							setDisplayTemplateId(event.target.value);
							setLoading(true);
						}}
						options={displayTemplateOptions}
						value={displayTemplateId}
					/>

					{loading && (
						<div className="pt-4">
							<ClayLoadingIndicator />
						</div>
					)}

					<iframe
						className={classNames('border-0 h-75 mt-4 w-100', {
							'd-none': loading,
						})}
						onLoad={() => {
							iframeRef.current.contentDocument.body.classList.add(
								'p-3'
							);

							iframeRef.current.contentDocument.body.addEventListener(
								'click',
								(event) => event.preventDefault(),
								{capture: true}
							);

							setLoading(false);
						}}
						ref={iframeRef}
						src={previewURL}
					/>
				</ClayForm.Group>
			</ClayModal.Body>
		</ClayModal>
	);
}

PreviewModal.propTypes = {
	observer: PropTypes.object.isRequired,
};

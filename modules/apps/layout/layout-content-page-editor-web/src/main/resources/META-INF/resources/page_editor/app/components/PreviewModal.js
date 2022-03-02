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

import {ClaySelect} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {addParams} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {LAYOUT_TYPES} from '../config/constants/layoutTypes';
import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {config} from '../config/index';
import {useDisplayPagePreviewItem} from '../contexts/DisplayPagePreviewItemContext';
import {useSelector} from '../contexts/StoreContext';
import selectLanguageId from '../selectors/selectLanguageId';
import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import {useId} from '../utils/useId';
import {DisplayPagePreviewItemSelector} from './DisplayPagePreviewItemSelector';
import Translation from './Translation';
import ViewportSizeSelector from './ViewportSizeSelector';

const PreviewModal = ({observer}) => {
	const initialSegmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const [languageId, setLanguageId] = useState(useSelector(selectLanguageId));
	const displayPagePreviewItem = useDisplayPagePreviewItem();

	const [viewportSize, setViewportSize] = useState(
		useSelector((state) => state.selectedViewportSize)
	);

	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const [segmentsExperienceId, setSegmentsExperienceId] = useState(
		initialSegmentsExperienceId
	);

	const availableSegmentsExperiences = useSelector(
		(state) => state.availableSegmentsExperiences
	);
	const experienceSelectId = useId();

	const previewURL = useMemo(() => {
		let nextURL = addParams(
			{
				[`${config.portletNamespace}languageId`]: languageId,
				[`${config.portletNamespace}segmentsExperienceId`]: segmentsExperienceId,
			},
			config.previewPageURL
		);

		if (
			config.layoutType === LAYOUT_TYPES.display &&
			displayPagePreviewItem
		) {
			nextURL = addParams(
				{
					[`${config.portletNamespace}className`]: displayPagePreviewItem
						.data.className,
					[`${config.portletNamespace}classPK`]: displayPagePreviewItem
						.data.classPK,
				},
				nextURL
			);
		}

		return nextURL;
	}, [languageId, segmentsExperienceId, displayPagePreviewItem]);

	const modalBodyWidth = useMemo(() => {
		const {maxWidth, minWidth} = config.availableViewportSizes[
			viewportSize
		];

		return viewportSize === VIEWPORT_SIZES.desktop
			? '100%'
			: (maxWidth + minWidth) / 2;
	}, [viewportSize]);

	return (
		<ClayModal
			className="page-editor__preview-modal"
			containerProps={{className: 'cadmin'}}
			observer={observer}
			size="full-screen"
		>
			<ClayModal.Header>
				<div className="d-flex flex-grow-1 justify-content-between page-editor__theme-adapter-buttons responsive-mode">
					<ul className="justify-content-start navbar-nav page-editor__preview-modal__part">
						{availableSegmentsExperiences && (
							<li className="mr-2 nav-item">
								<div className="align-middle d-inline-flex">
									<label
										className="mr-2 mt-1 w-100"
										htmlFor={experienceSelectId}
									>
										{Liferay.Language.get('experience')}
									</label>

									<ClaySelect
										aria-label="Experience"
										className="form-control-sm"
										id={experienceSelectId}
										onChange={(event) => {
											setSegmentsExperienceId(
												event.target.options[
													event.target.selectedIndex
												].value
											);
										}}
										value={segmentsExperienceId}
									>
										{availableSegmentsExperiences &&
											Object.keys(
												availableSegmentsExperiences
											).map((segmentsExperienceId) => (
												<ClaySelect.Option
													key={segmentsExperienceId}
													label={
														availableSegmentsExperiences[
															segmentsExperienceId
														].name
													}
													value={segmentsExperienceId}
												/>
											))}
									</ClaySelect>
								</div>
							</li>
						)}

						<li className="nav-item">
							<Translation
								availableLanguages={config.availableLanguages}
								defaultLanguageId={config.defaultLanguageId}
								dispatch={({languageId: nextLanguageId}) =>
									setLanguageId(nextLanguageId)
								}
								fragmentEntryLinks={fragmentEntryLinks}
								languageId={languageId}
								segmentsExperienceId={segmentsExperienceId}
							/>
						</li>
					</ul>

					<div className="page-editor__preview-modal__part">
						<ViewportSizeSelector
							onSizeSelected={setViewportSize}
							selectedSize={viewportSize}
						/>
					</div>

					<div className="justify-content-end page-editor__preview-modal__part pr-3">
						{config.layoutType === LAYOUT_TYPES.display ? (
							<DisplayPagePreviewItemSelector />
						) : null}
					</div>
				</div>
			</ClayModal.Header>

			<ClayModal.Body
				iFrameProps={{
					style: {maxWidth: modalBodyWidth},
				}}
				url={previewURL}
			/>
		</ClayModal>
	);
};

PreviewModal.propTypes = {
	observer: PropTypes.object.isRequired,
};

export default PreviewModal;

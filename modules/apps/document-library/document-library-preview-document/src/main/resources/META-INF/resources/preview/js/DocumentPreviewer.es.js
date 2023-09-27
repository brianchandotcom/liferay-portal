/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {debounce} from 'frontend-js-web';
import imagePromise from 'image-promise';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import '@liferay/document-library-preview-css';

const KEY_CODE_ENTER = 13;

const KEY_CODE_ESC = 27;

/**
 * Valid list of keycodes
 * Includes backspace, tab, arrows, delete and numbers
 * @type {Array<number>}
 */
const VALID_KEY_CODES = [
	8,
	9,
	37,
	38,
	39,
	40,
	46,
	48,
	49,
	50,
	51,
	52,
	53,
	54,
	55,
	56,
	57,
];

/**
 * Milisecons between goToPage calls
 * @type {number}
 */
const WAIT_BETWEEN_GO_TO_PAGE = 250;

/**
 * Component that creates a pdf preview
 * @review
 */

const DocumentPreviewer = ({baseImageURL, initialPage, totalPages}) => {
	const [currentPage, setCurrentPage] = useState(initialPage);
	const [currentPageLoading, setCurrentPageLoading] = useState(false);
	const [expanded, setExpanded] = useState(false);
	const [loadedPages] = useState({
		[currentPage]: {
			loaded: true,
			pagePromise: Promise.resolve(),
		},
	});
	const [nextPageDisabled, setNextPageDisabled] = useState(
		currentPage === totalPages
	);
	const [previousPageDisabled, setPreviousPageDisabled] = useState(
		currentPage === 1
	);
	const [showPageInput, setShowPageInput] = useState(false);

	const imageContainerRef = useRef();
	const pageInputRef = useRef();
	const showPageInputButtonRef = useRef();

	const isMounted = useIsMounted();

	if (showPageInput) {
		setTimeout(() => {
			if (isMounted()) {
				pageInputRef.current.focus();
			}
		}, 100);
	}

	const createImageURL = (page) => {
		const imageURL = new URL(baseImageURL);

		imageURL.searchParams.set('previewFileIndex', page);

		return imageURL.toString();
	};

	const loadPage = (page) => {
		let pagePromise = loadedPages[page] && loadedPages[page].pagePromise;

		if (!pagePromise) {
			pagePromise = imagePromise(createImageURL(page)).then(() => {
				loadedPages[page].loaded = true;
			});

			loadedPages[page] = {
				loaded: false,
				pagePromise,
			};
		}

		return pagePromise;
	};

	const loadAdjacentPages = (page, adjacentPageCount = 2) => {
		for (let i = 1; i <= adjacentPageCount; i++) {
			if (page + i <= totalPages) {
				loadPage(page + i);
			}

			if (page - i > 1) {
				loadPage(page - i);
			}
		}
	};

	const loadCurrentPage = debounce((page) => {
		loadPage(page)
			.then(() => {
				loadAdjacentPages(page);

				setCurrentPageLoading(false);
			})
			.catch(() => {
				setCurrentPageLoading(false);
			});
	}, WAIT_BETWEEN_GO_TO_PAGE);

	const goToPage = (page) => {
		setNextPageDisabled(page === totalPages);
		setPreviousPageDisabled(page === 1);

		if (!loadedPages[page] || !loadedPages[page].loaded) {
			setCurrentPageLoading(true);

			loadCurrentPage(page);
		}

		imageContainerRef.current.scrollTop = 0;

		setCurrentPage(page);
	};

	const processPageInput = (value) => {
		let pageNumber = Number.parseInt(value, 10);

		pageNumber = pageNumber
			? Math.min(Math.max(1, pageNumber), totalPages)
			: currentPage;

		goToPage(pageNumber);
	};

	const hidePageInput = (returnFocus = true) => {
		setShowPageInput(false);

		if (returnFocus) {
			setTimeout(() => {
				if (isMounted()) {
					showPageInputButtonRef.current.focus();
				}
			}, 100);
		}
	};

	const handleBlurPageInput = (event) => {
		processPageInput(event.currentTarget.value);

		hidePageInput(false);
	};

	const handleKeyDownPageInput = (event) => {
		const code = event.keyCode || event.charCode;

		if (code === KEY_CODE_ENTER) {
			processPageInput(event.currentTarget.value);

			hidePageInput();
		}
		else if (code === KEY_CODE_ESC) {
			hidePageInput();
		}
		else if (VALID_KEY_CODES.indexOf(code) === -1) {
			event.preventDefault();
		}
	};

	useEffect(() => {
		loadAdjacentPages(initialPage);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="preview-file">
			<div
				className="preview-file-container preview-file-max-height"
				ref={imageContainerRef}
			>
				{currentPageLoading ? (
					<ClayLoadingIndicator />
				) : (
					<img
						className={`preview-file-document ${
							!expanded && 'preview-file-document-fit'
						}`}
						src={createImageURL(currentPage)}
					/>
				)}
			</div>

			<div className="preview-toolbar-container">
				<ClayButton.Group className="floating-bar">
					<ClayButton.Group>
						<ClayButton
							className="btn-floating-bar btn-floating-bar-text"
							onClick={() => {
								setShowPageInput(true);
							}}
							ref={showPageInputButtonRef}
							title={
								totalPages > 1 &&
								Liferay.Language.get('click-to-jump-to-a-page')
							}
						>
							{`${Liferay.Language.get(
								'page'
							)} ${currentPage} / ${totalPages}`}
						</ClayButton>

						{showPageInput && (
							<div className="floating-bar-input-wrapper">
								<input
									className="floating-bar-input form-control form-control-sm"
									max={totalPages}
									min="1"
									onBlur={handleBlurPageInput}
									onKeyDown={handleKeyDownPageInput}
									placeholder={Liferay.Language.get(
										'page-...'
									)}
									ref={pageInputRef}
									type="number"
								/>
							</div>
						)}
					</ClayButton.Group>

					<ClayButton
						className="btn-floating-bar"
						disabled={previousPageDisabled}
						monospaced
						onClick={() => {
							goToPage(currentPage - 1);
						}}
						title={Liferay.Language.get('page-above')}
					>
						<ClayIcon symbol="caret-top" />
					</ClayButton>

					<ClayButton
						className="btn-floating-bar"
						disabled={nextPageDisabled}
						monospaced
						onClick={() => {
							goToPage(currentPage + 1);
						}}
						title={Liferay.Language.get('page-below')}
					>
						<ClayIcon symbol="caret-bottom" />
					</ClayButton>

					<div className="separator-floating-bar"></div>

					<ClayButton
						className="btn-floating-bar"
						monospaced
						onClick={() => {
							setExpanded(!expanded);
						}}
						title={
							expanded
								? Liferay.Language.get('zoom-to-fit')
								: Liferay.Language.get('expand')
						}
					>
						<ClayIcon
							symbol={expanded ? 'autosize' : 'full-size'}
						/>
					</ClayButton>
				</ClayButton.Group>
			</div>
		</div>
	);
};

DocumentPreviewer.propTypes = {
	baseImageURL: PropTypes.string,
	initialPage: PropTypes.number,
	totalPages: PropTypes.number,
};

export default DocumentPreviewer;

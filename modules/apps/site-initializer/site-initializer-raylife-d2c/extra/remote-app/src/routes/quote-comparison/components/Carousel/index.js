/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useCallback, useEffect, useState} from 'react';

const Carousel = ({
	cardElements,
	children,
	initialActiveIndex = 1,
	isMobileDevice,
	items = [],
	scrollableContainer,
}) => {
	const [activeIndex, setActiveIndex] = useState(initialActiveIndex);
	const [isFirstLoad, setIsFirstLoad] = useState(true);

	const handleSelected = useCallback(
		(selectedIndex) => {
			scrollableContainer.scrollTo(
				cardElements[selectedIndex].offsetLeft -
					(scrollableContainer.offsetWidth -
						cardElements[selectedIndex].offsetWidth) /
						2,
				0
			);
		},
		[cardElements, scrollableContainer]
	);

	const handleCenterCards = useCallback(() => {
		if (scrollableContainer) {
			scrollableContainer.scrollTo(
				(scrollableContainer.scrollWidth -
					scrollableContainer.offsetWidth) /
					2,
				0
			);
		}
		setIsFirstLoad(false);
	}, [scrollableContainer]);

	useEffect(() => {
		if (cardElements) {
			handleCenterCards();

			window.addEventListener('resize', handleCenterCards);
		}

		return () => {
			window.removeEventListener('resize', handleCenterCards);
		};
	}, [cardElements, handleCenterCards]);

	useEffect(() => {
		const setScrollCard = (scrollElement) => {
			if (!isFirstLoad) {
				const step =
					(scrollElement.scrollWidth - scrollElement.clientWidth) /
					items.length;

				const steps = items.map((_, index) => (index + 1) * step);

				for (const step of steps) {
					if (scrollElement.scrollLeft > step) {
						continue;
					}

					return setActiveIndex(steps.indexOf(step));
				}
			}
		};

		if (scrollableContainer) {
			setTimeout(() => {
				if (!isFirstLoad) {
					scrollableContainer.addEventListener('scroll', () => {
						setScrollCard(scrollableContainer);
					});
				}
			}, 1000);
		}

		return () => {
			if (scrollableContainer) {
				scrollableContainer.removeEventListener('scroll', () =>
					setScrollCard(scrollableContainer)
				);
			}
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [isFirstLoad, scrollableContainer]);

	return (
		<>
			{children}

			{isMobileDevice && (
				<div className="d-flex justify-content-center quote-comparison-mobile">
					{items.map((_, index) => (
						<div
							className={classNames('nav-button rounded', {
								'bg-brand-primary': activeIndex === index,
								'bg-neutral-2': activeIndex !== index,
							})}
							key={index}
							onClick={() => handleSelected(index)}
						/>
					))}
				</div>
			)}
		</>
	);
};

export default Carousel;

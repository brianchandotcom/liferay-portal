/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import React, {useState} from 'react';

import NoPreview from './NoPreview.es';
import PreviewImage from './PreviewImage.es';
import PreviewVideo from './PreviewVideo.es';

const Arrow = ({direction, handleClick}) => (
	<div className={`pull-${direction}`}>
		<ClayButton
			borderless
			className="icon-arrow"
			displayType="secondary"
			onClick={handleClick}
			size="lg"
		>
			<ClayIcon symbol={`angle-${direction}`} />
		</ClayButton>
	</div>
);

const InfoPanel = ({metadata}) => {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);

	const imageData = JSON.parse(metadata);

	const itemsHeader = imageData.groups.map((group, index) => {
		return (
			<ClayTabs.Item
				active={activeTabKeyValue === index}
				key={group.title}
				onClick={() => setActiveTabKeyValue(index)}
			>
				{group.title}
			</ClayTabs.Item>
		);
	});

	const itemsContent = imageData.groups.map((group, index) => {
		const itemContentTab = group.data.map((item) => {
			return (
				<React.Fragment key={item.key}>
					<dt className="sidebar-dt">{item.key}</dt>

					<dd className="sidebar-dd">{item.value}</dd>
				</React.Fragment>
			);
		});

		return (
			<ClayTabs.TabPane
				aria-labelledby={`tab-${index}`}
				key={`tabPane-${index}`}
			>
				<dl className="sidebar-dl sidebar-section">{itemContentTab}</dl>
			</ClayTabs.TabPane>
		);
	});

	return (
		<div className="info-panel sidenav-menu-slider">
			<div className="sidebar sidenav-menu">
				<div className="sidebar-header">
					<ClayTabs>{itemsHeader}</ClayTabs>
				</div>

				<div className="sidebar-body">
					<ClayTabs.Content activeIndex={activeTabKeyValue} fade>
						{itemsContent}
					</ClayTabs.Content>
				</div>
			</div>
		</div>
	);
};

const Carousel = ({
	currentItem,
	handleClickNext,
	handleClickPrevious,
	isImage,
	showArrows = true,
}) => {
	const isVideo = currentItem.type === 'video';
	let videoHtml = isVideo && currentItem?.value?.html;

	if (isVideo && typeof currentItem.value === 'string') {
		videoHtml = JSON.parse(currentItem.value).html;
	}

	return (
		<div className="carousel closed sidenav-container">
			<InfoPanel metadata={currentItem.metadata} />

			<div className="sidenav-content">
				{showArrows && (
					<Arrow direction="left" handleClick={handleClickPrevious} />
				)}

				{isVideo ? (
					<PreviewVideo html={videoHtml} />
				) : isImage ? (
					<PreviewImage
						src={currentItem.url || currentItem.base64}
						title={currentItem.title}
					/>
				) : (
					<NoPreview />
				)}

				{showArrows && (
					<Arrow direction="right" handleClick={handleClickNext} />
				)}
			</div>
		</div>
	);
};

export default Carousel;

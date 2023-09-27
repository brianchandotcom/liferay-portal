/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import React from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const ASPECT_RATIO_POSITIONS = [
	'aspect-ratio-item-top-left',
	'aspect-ratio-item-top-center',
	'aspect-ratio-item-top-right',
	'aspect-ratio-item-right-middle',
	'aspect-ratio-item-bottom-right',
	'aspect-ratio-item-bottom-center',
	'aspect-ratio-item-bottom-left',
	'aspect-ratio-item-left-middle',
	'aspect-ratio-item-center-middle',
	'label',
];

const ASPECT_RATIO_TYPES = ['flush', 'fluid'];

const AUTOFIT_COL_SIZES = [
	'autofit-col-sm',
	'autofit-col-md',
	'autofit-col-lg',
];

const CONTAINER_ASPECT_RATIOS = ['1', '2', '3', '4', '5', '6'];

const DESCRIPTION =
	'Sagittis, eu pretium massa quisque cursus augue massa cursus. Sed quisque velit, auctor at lobortis hac tincidunt sodales id. Elit interdum vel nisi, in enim sagittis at. Netus sagittis eleifend aliquet urna quis.';

const DIALECT_TYPES = [
	{
		className: '',
		label: 'Elevated',
	},
	{
		className: 'card-outlined',
		label: 'Outlined',
	},
	{
		className: 'card-flat',
		label: 'Flat',
	},
];

const DISPLAY_TYPES = ['file', 'image', 'user'];

const SUBTITLE = 'Joe Bloggs';

const TITLE = 'deliverable.doc';

const CardGuide = () => {
	return (
		<>
			<TokenGroup
				group="form"
				title={Liferay.Language.get('dialect-card-types')}
			>
				{DIALECT_TYPES.map((dialectType, i) => (
					<TokenItem
						key={`${i}`}
						label={dialectType.label}
						size="small"
					>
						<ClayCard className={dialectType.className}>
							<ClayCard.AspectRatio className="card-item-first">
								<img
									className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-flush"
									src="https://via.placeholder.com/160"
								/>

								<ClayLabel
									className="aspect-ratio-item aspect-ratio-item-bottom-left m-0"
									displayType="inverse-success"
								>
									Label
								</ClayLabel>
							</ClayCard.AspectRatio>

							<ClayCard.Body>
								<ClayCard.Row>
									<div className="autofit-col autofit-col-expand">
										<section className="autofit-section">
											<ClayCard.Description
												className="mb-1"
												displayType="title"
											>
												{TITLE}
											</ClayCard.Description>

											<ClayCard.Description displayType="subtitle">
												{SUBTITLE}
											</ClayCard.Description>

											<ClayCard.Description
												className="mt-2 text-paragraph-sm"
												displayType="text"
												truncate={false}
											>
												{DESCRIPTION}
											</ClayCard.Description>
										</section>
									</div>
								</ClayCard.Row>
							</ClayCard.Body>
						</ClayCard>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="form"
				title={Liferay.Language.get('dialect-card-types-interactive')}
			>
				{DIALECT_TYPES.map((dialectType, i) => (
					<TokenItem
						key={`${i}`}
						label={`${dialectType.label} Interactive`}
						size="small"
					>
						<ClayCard
							className={`${dialectType.className} interactive`}
							tabIndex="0"
						>
							<ClayCard.AspectRatio className="card-item-first">
								<img
									className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-flush"
									src="https://via.placeholder.com/160"
								/>

								<ClayLabel
									className="aspect-ratio-item aspect-ratio-item-bottom-left m-0"
									displayType="inverse-success"
								>
									Label
								</ClayLabel>
							</ClayCard.AspectRatio>

							<ClayCard.Body>
								<ClayCard.Row>
									<div className="autofit-col autofit-col-expand">
										<section className="autofit-section">
											<ClayCard.Description
												className="mb-1"
												displayType="title"
											>
												{TITLE}
											</ClayCard.Description>

											<ClayCard.Description displayType="subtitle">
												{SUBTITLE}
											</ClayCard.Description>

											<ClayCard.Description
												className="mt-2 text-paragraph-sm"
												displayType="text"
												truncate={false}
											>
												{DESCRIPTION}
											</ClayCard.Description>
										</section>
									</div>
								</ClayCard.Row>
							</ClayCard.Body>
						</ClayCard>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="form"
				title={Liferay.Language.get('clay-card-display-types')}
			>
				{DISPLAY_TYPES.map((displayType, i) => (
					<TokenItem
						key={`${i}`}
						label={`${displayType}`}
						size="small"
					>
						<ClayCard displayType={displayType}>
							<ClayCard.AspectRatio className="card-item-first">
								<img
									className="aspect-ratio-item aspect-ratio-item-center-middle"
									src="https://via.placeholder.com/160"
								/>

								<ClayLabel
									className="aspect-ratio-item aspect-ratio-item-bottom-left m-0"
									displayType="inverse-success"
								>
									Label
								</ClayLabel>
							</ClayCard.AspectRatio>

							<ClayCard.Body>
								<ClayCard.Row>
									<div className="autofit-col autofit-col-expand">
										<section className="autofit-section">
											<ClayCard.Description
												className="mb-1"
												displayType="title"
											>
												{TITLE}
											</ClayCard.Description>

											<ClayCard.Description displayType="subtitle">
												{SUBTITLE}
											</ClayCard.Description>

											<ClayCard.Description
												className="mt-2 text-paragraph-sm"
												displayType="text"
												truncate={false}
											>
												{DESCRIPTION}
											</ClayCard.Description>
										</section>
									</div>
								</ClayCard.Row>
							</ClayCard.Body>
						</ClayCard>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="form"
				title={Liferay.Language.get('aspect-ratio-types')}
			>
				{ASPECT_RATIO_TYPES.map((aspectRatioType, i) => (
					<TokenItem
						key={`${i}`}
						label={`aspect-ratio-item-${aspectRatioType}`}
						size="small"
					>
						<ClayCard>
							<ClayCard.AspectRatio className="card-item-first">
								<img
									className={`aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-${aspectRatioType}`}
									src="https://via.placeholder.com/160"
								/>

								<ClayLabel
									className="aspect-ratio-item aspect-ratio-item-bottom-left m-0"
									displayType="inverse-success"
								>
									Label
								</ClayLabel>
							</ClayCard.AspectRatio>

							<ClayCard.Body>
								<ClayCard.Row>
									<div className="autofit-col autofit-col-expand">
										<section className="autofit-section">
											<ClayCard.Description
												className="mb-1"
												displayType="title"
											>
												{TITLE}
											</ClayCard.Description>

											<ClayCard.Description displayType="subtitle">
												{SUBTITLE}
											</ClayCard.Description>

											<ClayCard.Description
												className="mt-2 text-paragraph-sm"
												displayType="text"
												truncate={false}
											>
												{DESCRIPTION}
											</ClayCard.Description>
										</section>
									</div>
								</ClayCard.Row>
							</ClayCard.Body>
						</ClayCard>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="form"
				title={Liferay.Language.get('container-aspect-ratios')}
			>
				{CONTAINER_ASPECT_RATIOS.map((containerAspectRatio, i) => (
					<TokenItem
						key={`${i}`}
						label={`aspect-ratio-${containerAspectRatio}`}
						size="small"
					>
						<ClayCard>
							<ClayCard.AspectRatio
								className="card-item-first"
								containerAspectRatio={containerAspectRatio}
							>
								<img
									className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-flush"
									src="https://via.placeholder.com/160"
								/>

								<ClayLabel
									className="aspect-ratio-item aspect-ratio-item-bottom-left m-0"
									displayType="inverse-success"
								>
									Label
								</ClayLabel>
							</ClayCard.AspectRatio>

							<ClayCard.Body>
								<ClayCard.Row>
									<div className="autofit-col autofit-col-expand">
										<section className="autofit-section">
											<ClayCard.Description
												className="mb-1"
												displayType="title"
											>
												{TITLE}
											</ClayCard.Description>

											<ClayCard.Description displayType="subtitle">
												{SUBTITLE}
											</ClayCard.Description>

											<ClayCard.Description
												className="mt-2 text-paragraph-sm"
												displayType="text"
												truncate={false}
											>
												{DESCRIPTION}
											</ClayCard.Description>
										</section>
									</div>
								</ClayCard.Row>
							</ClayCard.Body>
						</ClayCard>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="form"
				title={Liferay.Language.get('aspect-ratio-positions')}
			>
				{ASPECT_RATIO_POSITIONS.map((aspectRatioPosition, i) => (
					<TokenItem key={i} label={aspectRatioPosition} size="small">
						<ClayCard>
							<ClayCard.AspectRatio className="card-item-first">
								<img
									className="aspect-ratio-item aspect-ratio-item-flush"
									src="https://via.placeholder.com/160"
								/>

								<ClayLabel
									className={`aspect-ratio-item m-0 ${aspectRatioPosition}`}
									displayType="inverse-success"
								>
									Label
								</ClayLabel>
							</ClayCard.AspectRatio>

							<ClayCard.Body>
								<ClayCard.Row>
									<div className="autofit-col autofit-col-expand">
										<section className="autofit-section">
											<ClayCard.Description
												className="mb-1"
												displayType="title"
											>
												{TITLE}
											</ClayCard.Description>

											<ClayCard.Description displayType="subtitle">
												{SUBTITLE}
											</ClayCard.Description>

											<ClayCard.Description
												className="mt-2 text-paragraph-sm"
												displayType="text"
												truncate={false}
											>
												{DESCRIPTION}
											</ClayCard.Description>
										</section>
									</div>
								</ClayCard.Row>
							</ClayCard.Body>
						</ClayCard>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="form"
				title={Liferay.Language.get('horizontal-cards')}
			>
				{AUTOFIT_COL_SIZES.map((autofitColSize, i) => (
					<TokenItem key={`${i}`} label={autofitColSize} size="large">
						<ClayCard horizontal>
							<ClayCard.Row>
								<div
									className={`autofit-col ${autofitColSize}`}
								>
									<ClayCard.AspectRatio className="card-item-first">
										<img
											className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-flush"
											src="https://via.placeholder.com/160"
										/>

										<ClayLabel
											className="aspect-ratio-item aspect-ratio-item-bottom-left m-0"
											displayType="inverse-success"
										>
											Label
										</ClayLabel>
									</ClayCard.AspectRatio>
								</div>

								<div className="autofit-col autofit-col-expand">
									<section className="autofit-section">
										<ClayCard.Body>
											<ClayCard.Description
												className="mb-1"
												displayType="title"
											>
												{TITLE}
											</ClayCard.Description>

											<ClayCard.Description displayType="subtitle">
												{SUBTITLE}
											</ClayCard.Description>

											<ClayCard.Description
												className="mt-2 text-paragraph-sm"
												displayType="text"
											>
												{DESCRIPTION}
											</ClayCard.Description>
										</ClayCard.Body>
									</section>
								</div>
							</ClayCard.Row>
						</ClayCard>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup group="form" title={Liferay.Language.get('icon')}>
				<TokenItem size="small">
					<ClayCard>
						<ClayCard.AspectRatio className="card-item-first">
							<div className="aspect-ratio-item aspect-ratio-item-center-middle card-type-asset-icon">
								<ClayIcon symbol="documents-and-media" />
							</div>
						</ClayCard.AspectRatio>

						<ClayCard.Body>
							<ClayCard.Row>
								<div className="autofit-col autofit-col-expand">
									<section className="autofit-section">
										<ClayCard.Description
											className="mb-1"
											displayType="title"
										>
											{TITLE}
										</ClayCard.Description>

										<ClayCard.Description displayType="subtitle">
											{SUBTITLE}
										</ClayCard.Description>

										<ClayCard.Description
											className="mt-2 text-paragraph-sm"
											displayType="text"
											truncate={false}
										>
											{DESCRIPTION}
										</ClayCard.Description>
									</section>
								</div>
							</ClayCard.Row>
						</ClayCard.Body>
					</ClayCard>
				</TokenItem>
			</TokenGroup>
		</>
	);
};

export default CardGuide;

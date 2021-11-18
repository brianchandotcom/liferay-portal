import ClayCard from '@clayui/card';
import classNames from 'classnames';
import BaseButton from '~/common/components/BaseButton';
import StatusTag from '../StatusTag';
import ProjectCardSkeleton from './Skeleton';

const getCurrentEndDate = (currentEndDate) => {
	const date = new Date(currentEndDate);
	const month = date.toLocaleDateString('default', {month: 'short'});
	const day = date.getDate();
	const year = date.getFullYear();

	return `${month} ${day}, ${year}`;
};

const ProjectCard = ({code, isSmall, onClick, region, sla, status, title}) => (
	<ClayCard
		className={classNames('m-0', {
			'project-card': !isSmall,
			'project-card-sm': isSmall,
		})}
		onClick={onClick}
	>
		<ClayCard.Body
			className={classNames('d-flex h-100 justify-content-between', {
				'flex-column': !isSmall,
				'flex-row': isSmall,
			})}
		>
			<ClayCard.Description
				className="text-neutral-3"
				displayType="title"
				tag={isSmall ? 'h4' : 'h3'}
			>
				{title}
				{isSmall && (
					<div className="font-weight-lighter subtitle text-neutral-5 text-paragraph text-uppercase">
						{code}
					</div>
				)}
			</ClayCard.Description>

			<div
				className={classNames('d-flex justify-content-between', {
					'align-items-end': isSmall,
				})}
			>
				<ClayCard.Description
					displayType="text"
					tag="div"
					title={null}
					truncate={false}
				>
					<StatusTag currentStatus={status} />

					<div
						className={classNames(
							'text-paragraph-sm',
							'text-neutral-5',
							{
								'my-1': !isSmall,
								'sm-mb': isSmall,
							}
						)}
					>
						Ends on{' '}
						<span className="font-weight-bold text-paragraph">
							{getCurrentEndDate(sla.currentEndDate)}
						</span>
					</div>

					{isSmall && (
						<div className="text-neutral-5 text-paragraph-sm">
							Support Region{' '}
							<span className="font-weight-bold">{region}</span>
						</div>
					)}
				</ClayCard.Description>

				{!isSmall && (
					<BaseButton
						appendIcon="angle-right"
						borderless
						className="p-0 text-brand-primary"
					>
						See details
					</BaseButton>
				)}
			</div>
		</ClayCard.Body>
	</ClayCard>
);

ProjectCard.Skeleton = ProjectCardSkeleton;

export default ProjectCard;

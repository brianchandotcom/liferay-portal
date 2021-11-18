import ClayCard from '@clayui/card';
import Skeleton from '~/common/components/Skeleton';

const ProjectCardSkeleton = () => {
	return (
		<ClayCard className="m-0 project-card">
			<ClayCard.Body className="d-flex flex-column h-100 justify-content-between">
				<ClayCard.Description displayType="title" tag="div">
					<Skeleton height={32} width={136} />
				</ClayCard.Description>

				<div className="d-flex justify-content-between">
					<ClayCard.Description
						displayType="text"
						tag="div"
						title={null}
						truncate={false}
					>
						<Skeleton.Rounded height={20} width={49} />

						<Skeleton className="my-1" height={24} width={84} />
					</ClayCard.Description>

					<Skeleton className="my-1" height={24} width={75} />
				</div>
			</ClayCard.Body>
		</ClayCard>
	);
};

export default ProjectCardSkeleton;

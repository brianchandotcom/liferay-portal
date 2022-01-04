import Skeleton from '../../../../common/components/Skeleton';

const ProjectContactsSkeleton = () => {
	return (
		<div className="container mb-5 mx-0 project-contacs-container">
			<div className="row">
				<div className="col-5"></div>

				<div className="col-7">
					<Skeleton className="mb-4" height={20} width={150} />

					<Skeleton className="mb-1" height={24} width={130} />

					<Skeleton className="mb-1" height={24} width={144} />

					<Skeleton height={16} width={188} />
				</div>
			</div>
		</div>
	);
};

export default ProjectContactsSkeleton;

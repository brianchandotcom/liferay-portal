import Skeleton from '../../../../common/components/Skeleton';

const ProjectContactsSkeleton = () => {
	return (
		<div>
			<h5 className="mb-4 rounded-sm text-neutral-10">Liferay Contact</h5>

			<Skeleton className="mb-1" height={24} width={107} />

			<Skeleton className="mb-1" height={24} width={120} />

			<Skeleton height={16} width={150} />
		</div>
	);
};

export default ProjectContactsSkeleton;

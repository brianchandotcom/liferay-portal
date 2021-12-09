import Skeleton from '../../../../common/components/Skeleton';

const ActivationKeysSkeleton = () => {
	return (
		<div>
			<Skeleton className="my-4" height={40} width={305} />

			<Skeleton className="mt-3" height={24} width={620} />

			<div className="d-flex mt-3">
				<div className="mr-3">
					<Skeleton className="mb-1" height={24} width={90} />

					<Skeleton.Rounded height={48} width={247} />
				</div>

				<div>
					<Skeleton className="mb-1" height={24} width={130} />

					<Skeleton height={48} width={360} />
				</div>
			</div>
		</div>
	);
};

export default ActivationKeysSkeleton;

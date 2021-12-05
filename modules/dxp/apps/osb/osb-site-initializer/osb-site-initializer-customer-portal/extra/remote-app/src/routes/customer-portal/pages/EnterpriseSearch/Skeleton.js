import Skeleton from '../../../../common/components/Skeleton';

const EnterpriseSearchSkeleton = () => {
	return (
		<div>
			<div className="mb-5">
				<Skeleton height={24} width={425} />
			</div>

			<div className="mb-4">
				<Skeleton height={16} width={720} />
			</div>

			<div className="d-flex">
				<div className="mr-4">
					<Skeleton className="mb-2" height={8} width={120} />

					<Skeleton height={16} width={320} />
				</div>

				<div className="mr-4">
					<Skeleton className="mb-2" height={8} width={120} />

					<Skeleton height={16} width={450} />
				</div>
			</div>
		</div>
	);
};

export default EnterpriseSearchSkeleton;

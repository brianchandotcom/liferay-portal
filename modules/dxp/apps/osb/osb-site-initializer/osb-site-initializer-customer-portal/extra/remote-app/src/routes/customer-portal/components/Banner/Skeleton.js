import {useContext} from 'react';
import Skeleton from '~/common/components/Skeleton';
import {AppContext} from '../../context';

const BannerSkeleton = () => {
	const [{assetsPath}] = useContext(AppContext);

	return (
		<div className="banner d-flex flex-column ml-5 rounded-left">
			<div className="d-flex flex-column h-100 justify-content-between m-3 p-5 text-neutral-10">
				<div className="display-4 font-weight-normal">
					<Skeleton height={50} width={500} />
				</div>

				<div>
					<Skeleton height={36} width={200} />

					<div className="pt-1">
						<Skeleton height={24} width={700} />
					</div>
				</div>
			</div>

			<img
				className="position-absolute"
				src={`${assetsPath}/assets/banner_background_customer_portal.svg`}
			/>
		</div>
	);
};

export default BannerSkeleton;

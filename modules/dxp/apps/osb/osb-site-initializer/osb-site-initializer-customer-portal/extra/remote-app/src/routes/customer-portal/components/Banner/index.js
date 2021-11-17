import {useContext} from 'react';
import {AppContext} from '../../context';
import BannerSkeleton from './Skeleton';

const Banner = ({userName}) => {
	const [{assetsPath}] = useContext(AppContext);

	return (
		<div className="banner d-flex flex-column ml-5 rounded-left">
			<div className="d-flex flex-column h-100 justify-content-between m-3 p-5 text-neutral-10">
				<div className="display-4 font-weight-normal">
					Welcome,{' '}
					<span className="font-weight-bolder">{userName}!</span>
				</div>

				<div>
					<h2>Projects</h2>

					<div className="font-weight-normal text-paragraph">
						Select a project to see your subscriptions, activate
						your products, and view team members.
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

Banner.Skeleton = BannerSkeleton;

export default Banner;

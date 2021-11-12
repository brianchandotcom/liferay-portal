import Skeleton from '~/common/components/Skeleton';
import Layout from '../../components/Layout';

const WelcomeSkeleton = () => {
	return (
		<Layout
			className="align-items-center d-flex flex-column pt-5 px-6"
			footerProps={{
				middleButton: <Skeleton.Rounded height={48} width={110} />,
			}}
			headerSkeleton={
				<div className="p-4">
					<Skeleton className="mb-4" height={8} width={105} />

					<Skeleton height={16} width={425} />
				</div>
			}
		>
			<Skeleton.Square height={200} width={320} />

			<Skeleton
				align="center"
				className="d-flex flex-column justify-content-center my-auto"
				count={2}
				height={8}
				width={400}
			/>
		</Layout>
	);
};
export default WelcomeSkeleton;

import RoundedSkeleton from '~/common/components/skeleton/rounded-skeleton';
import SquareSkeleton from '~/common/components/skeleton/square-skeleton';
import TextSkeleton from '~/common/components/skeleton/text-skeleton';
import Layout from '../layout';

const WelcomeSkeleton = () => {
	return (
		<Layout
			footerProps={{
				middleButton: <RoundedSkeleton height={48} width={110} />,
			}}
			headerSkeleton={
				<div className="p-4">
					<TextSkeleton className="mb-4" height={8} width={105} />

					<TextSkeleton height={16} width={425} />
				</div>
			}
			mainStyles="align-items-center d-flex flex-column pt-5 px-6"
		>
			<SquareSkeleton height={200} width={320} />

			<TextSkeleton
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

import classNames from 'classnames';
import Footer from './Footer';
import Header from './Header';

const Layout = ({
	children,
	className,
	footerProps,
	headerProps,
	headerSkeleton,
}) => (
	<div className="border d-flex flex-column mb-7 mx-auto onboarding rounded-lg shadow-lg">
		{headerProps ? <Header {...headerProps} /> : headerSkeleton}

		<main className={classNames('flex-grow-1', 'overflow-auto', className)}>
			{children}
		</main>

		<Footer {...footerProps} />
	</div>
);
export default Layout;

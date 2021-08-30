import LiferayLogo from './assets/tree.png';

const App = () => {
	return (
		<div className="container">
			<div className="content">
				<h1>Welcome to Liferay</h1>

				<p>Liferay Community Edition</p>
				<span>Today: {new Date().toLocaleDateString()}</span>
			</div>

			<img alt="logo" draggable={false} height={200} src={LiferayLogo} />
		</div>
	);
};

export default App;

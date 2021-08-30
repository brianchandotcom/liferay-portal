const App = () => {
	return (
		<div className="container">
			<div className="content">
				<h1>Welcome to Liferay</h1>

				<p>Liferay Community Edition</p>
				<span>Today: {new Date().toLocaleDateString()}</span>
			</div>
		</div>
	);
};

export default App;

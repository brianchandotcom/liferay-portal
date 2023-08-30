import React, { useEffect, useState } from 'react';

import AddUserForm from '../AddUserForm';

import { getUsers } from './request';

function App() {
	const [users, setUsers] = useState([]);

	useEffect(() => {
		getUsers().then(res => {
			setUsers(res.items);
		})
	}, []);

	return (
		<div className="row">
			<div className="col-2">
				<h1>Users:</h1>

				{users.map(user => (
					<div key={user.id}>
						{user.givenName} {user.familyName}
					</div>
				))}
			</div>
			<div className="col-8">
				<AddUserForm />
			</div>
		</div>
	);
}

export default App;
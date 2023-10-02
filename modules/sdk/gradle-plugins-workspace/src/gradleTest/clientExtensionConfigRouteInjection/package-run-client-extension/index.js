const fs = require('fs');
const path = require('path');

fs.writeFileSync(
	path.join(__dirname, 'liferay.routes.client.extension'),
	process.env.LIFERAY_ROUTES_CLIENT_EXTENSION || "not found");
fs.writeFileSync(
	path.join(__dirname, 'liferay.routes.dxp'),
	process.env.LIFERAY_ROUTES_DXP || "not found");
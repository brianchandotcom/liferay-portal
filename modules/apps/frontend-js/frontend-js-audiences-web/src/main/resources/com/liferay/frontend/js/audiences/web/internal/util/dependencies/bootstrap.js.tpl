const BASE_URL = new URL(import.meta.url.replace(/\?.*/, '') + '/../../../..');

const {audiences} = await import(`${BASE_URL}o/frontend-js-audiences-web/__liferay__/index.js`);

audiences.setLogEnabled([$LOG_ENABLED$]);

audiences.clear('PAGE');
await audiences.runDetection(`${BASE_URL}o/audiences/definition.([$AUDIENCES_DEFINITION_HASH$]).json`);

await import(`${BASE_URL}o/audiences/[$PLID$]/variations.([$ELEMENT_VARIATIONS_HASH$]).js`);

await audiences.runHandlers();
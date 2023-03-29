@spira_Data_Source @Data_Source @DXP @team_FARO @priority_5
Feature: Sync Liferay Data Source Contacts
	As an Business User, I should be able to sync Contacts from a Liferay Data Source

	Background: [Setup]
		* I set up the local DXP instance
		* I add an OAuth2 application on the local DXP instance
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Liferay DXP" button
		* I click the "Connect with OAuth" button
		* I type "SyncLiferayDataSourceContacts - ${Random.1}" into the "Name" input field
		* I type "${NGROK_URL}/" into the "URL" input field
		* I type "${OAUTH2_CLIENT_ID}" into the "Consumer Key/Client ID" input field
		* I type "${OAUTH2_SECRET}" into the "Consumer Secret/Client Secret" input field
		* I click the "Authorize & Save" button
		* I accept the Liferay DXP OAuth2 Popup
		* I should see that "SyncLiferayDataSourceContacts - ${Random.1}" was successfully authenticated
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click SyncLiferayDataSourceContacts - ${Random.1} in the table

	Scenario: Configure Contacts for a Liferay DXP Data Source - Sync All
		Given I click the "Configure Data Source" tab
		And I click the button for Contacts Configuration
		When I click the Sync All toggle switch
		And I click the "Save and Continue" button
		And I click the "Configure" button
		And I should see that the DXP Contacts data is synced
		And I click the button for Analytics Configuration
		And I click the select all checkbox in the toolbar
		And I click the "Configure" button
		And I should see that the DXP Contacts data is synced
		And I add activity to user
		And I go to the "Sites" page
		And I click the Property Dropdown Menu
		And I click "SyncLiferayDataSourceContacts - ${Random.1}" in the Property Dropdown Menu
		And I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "Test Test"
		Then I should see an item named "Test Test" in the table
		When I click "Test Test" in the table
		And I click the "Details" tab
		Then I should see the following rows in the Contact Details tab
			| email         | test@liferay.com | emailAddress          | SyncLiferayDataSourceContacts - ${Random.1} |

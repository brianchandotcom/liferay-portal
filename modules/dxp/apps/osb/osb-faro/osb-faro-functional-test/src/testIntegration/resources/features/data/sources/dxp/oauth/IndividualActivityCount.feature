@spira_Activities @team_FARO @priority_4 @blocked
Feature: Individual Activity Count is reduced when a Page with Activities is removed
	As a business user, I should see that an Individual's Activity Count is reduced when a Page with Activities is removed

	Background: [Setup] Add a DXP Data Source and generate Activities on a Page
		* I set up the local DXP instance
		* I add an OAuth2 application on the local DXP instance
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Liferay DXP" button
		* I click the "Connect with OAuth" button
		* I type "IndividualActivityCount A - ${Random.1}" into the "Name" input field
		* I type "${NGROK_URL}/" into the "URL" input field
		* I type "${OAUTH2_CLIENT_ID}" into the "Consumer Key/Client ID" input field
		* I type "${OAUTH2_SECRET}" into the "Consumer Secret/Client Secret" input field
		* I click the "Authorize & Save" button
		* I accept the Liferay DXP OAuth2 Popup
		* I should see that "IndividualActivityCount A - ${Random.1}" was successfully authenticated
		* I click the "Configure Data Source" tab
		* I click the button for Contacts Configuration
		* I click the Sync All toggle switch
		* I click the "Save and Continue" button
		* I click the "Configure" button
		* I go to the "Data Source" page
		* I click "IndividualActivityCount A - ${Random.1}" in the table
		* I click the "Configure Data Source" tab
		* I click the button for Analytics Configuration
		* I click the select all checkbox in the toolbar
		* I click the "Configure" button
		* I create a dummy page called "${Random.2} Page" on the DXP Site
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "IndividualActivityCount - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist
			test@liferay.com,Test,Test,Civil Engineer
			"""
		* I click the "CSV File" button
		* I browse for a file named "IndividualActivityCount - ${Random.1}.csv"
		* I name the Data Source "IndividualActivityCount - ${Random.1}.csv"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "IndividualActivityCount - ${Random.1}.csv" was successfully uploaded

	Scenario: Individual Activity Count is reduced when a Page with Activities is removed
		Given I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "Test Test"
		And I should see that the "Total Activities" for "Test Test" is "5"
		Given I go to the "Data Source" page
		And I click "IndividualActivityCount A - ${Random.1}" in the table
		When I click the "Delete Data Source" button
		And I delete the "IndividualActivityCount A - ${Random.1}" Data Source
		Given I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "Test Test"
		And I should see that the "Total Activities" for "Test Test" is "0"
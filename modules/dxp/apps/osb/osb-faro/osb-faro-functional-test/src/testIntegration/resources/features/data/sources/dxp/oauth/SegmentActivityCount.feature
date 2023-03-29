@spira_Activities @team_FARO @priority_4 @blocked
Feature: Segment Activity Count is reduced when a Page with Activities is removed
	As a business user, I should see that an Segment's Activity Count is reduced when a Page with Activities is removed

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
		* I type "SegmentActivityCount A - ${Random.1}" into the "Name" input field
		* I type "${NGROK_URL}/" into the "URL" input field
		* I type "${OAUTH2_CLIENT_ID}" into the "Consumer Key/Client ID" input field
		* I type "${OAUTH2_SECRET}" into the "Consumer Secret/Client Secret" input field
		* I click the "Authorize & Save" button
		* I accept the Liferay DXP OAuth2 Popup
		* I should see that "SegmentActivityCount A - ${Random.1}" was successfully authenticated
		* I click the "Configure Data Source" tab
		* I click the button for Contacts Configuration
		* I click the Sync All toggle switch
		* I click the "Save and Continue" button
		* I click the "Configure" button
		* I go to the "Data Source" page
		* I click "SegmentActivityCount A - ${Random.1}" in the table
		* I click the "Configure Data Source" tab
		* I click the button for Analytics Configuration
		* I click the select all checkbox in the toolbar
		* I click the "Configure" button
		* I create a dummy page called "${Random.2} Page" on the DXP Site
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "SegmentActivityCount - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist
			test@liferay.com,Test,Test,Civil Engineer
			"""
		* I click the "CSV File" button
		* I browse for a file named "SegmentActivityCount - ${Random.1}.csv"
		* I name the Data Source "SegmentActivityCountCSV - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "SegmentActivityCountCSV - ${Random.1}" was successfully uploaded
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Test Test"
		* I click the checkbox on the table row containing "Test Test"
		* I click the "Add" button
		* I click the "View Added Members" button
		* I name the Static segment "SegmentActivityCountSegment - ${Random.1}" and save it

	Scenario: Segment Activity Count is reduced when a Page with Activities is removed
		Given I go to the "Segments" page
		And I search for "SegmentActivityCountSegment - ${Random.1}"
		And I should see that the "Total Activities" for "SegmentActivityCountSegment - ${Random.1}" is "5"
		Given I go to the "Data Source" page
		And I click "SegmentActivityCount A - ${Random.1}" in the table
		When I click the "Delete Data Source" button
		And I delete the "SegmentActivityCount A - ${Random.1}" Data Source
		Given I go to the "Segments" page
		And I search for "SegmentActivityCountSegment - ${Random.1}"
		And I should see that the "Total Activities" for "SegmentActivityCountSegment - ${Random.1}" is "0"

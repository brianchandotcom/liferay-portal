@spira_Accounts @Accounts @team_FARO @priority_5
Feature: Assert an Account's Associated Segments
	As an Business User, I should be able to see a list of segments associated with the account

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert a Segment Associated with an Account
		Given I go to the "Segments" page
		And I click the "Create Segment" button
		And I click the "Static Segment" dropdown option
		And I click the "Add Members" button
		And I search for "Daniela Jacobson"
		And I click the checkbox on the table row containing "Daniela Jacobson"
		And I click the "Add" button
		And I name the Static segment "AssertAccountSegments Segment - ${Random.1}" and save it
		When I go to the "Accounts" page
		And I search for "Lesch, Walsh and Stracke"
		And I click "Lesch, Walsh and Stracke" in the table
		And I click the "Segments" tab
		Then I should see a "Segment" named "AssertAccountSegments Segment - ${Random.1}" with "1" items
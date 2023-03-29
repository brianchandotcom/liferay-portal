@spira_Segments @Segments @Profile @team_FARO @priority_4
Feature: Assert the Segments Overview Cards
	As an Business User, I should be able to assert the cards on the Overview tab of a Segment's page

	Background: [Setup] Create a Static Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Abram Bauch"
		* I click the checkbox on the table row containing "Abram Bauch"
		* I click the "Add" button
		* I click the "View Added Members" button
		* I name the Static segment "AssertSegmentsOverviewCards - ${Random.1}" and save it

	Scenario: Assert Segment Overview Profile Cards
		Then I should see the Segment Profile Cards with their details
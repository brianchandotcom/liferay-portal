@spira_Individuals @Individuals @team_FARO @priority_5
Feature: Assert an Individual's Segments
	As an Business User, I should be able to add an Individual to a segment and assert the Individual's Segment tab

	Background: [Setup] Upload CSV Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Adrienne Johnston"
		* I click the checkbox on the table row containing "Adrienne Johnston"
		* I click the "Add" button
		* I click the "View Added Members" button
		* I name the Static segment "AssertIndividualsSegments - ${Random.1}" and save it

	Scenario: Assert Segments Tab
		Given I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "Adrienne Johnston"
		And I click "Adrienne Johnston" in the table
		When I click the "Segments" tab
		Then I should see "AssertIndividualsSegments - ${Random.1}" in a table

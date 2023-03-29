@spira_Segments @Segments @Static @Creation @team_FARO @priority_3
Feature: Undo all members from being added
	As a Business User, I should be able to undo all members from being added

	Background: [Setup] Navigate to Static Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option

	Scenario: Undo all members from being added
		Given I click the "Add Members" button
		And I click the checkbox on the table row containing "Abram Bauch"
		And I click the checkbox on the table row containing "Alana Nicolas"
		And I click the "Add" button
		And I click the "View Added Members" button
		And I click the select all checkbox in the toolbar
		When I click the "Undo Changes" button
		Then I should not see an individual named "Abram Bauch" in the table
		And I should not see an individual named "Alana Nicolas" in the table
		And I should see text saying "There are no Individuals found." on the page
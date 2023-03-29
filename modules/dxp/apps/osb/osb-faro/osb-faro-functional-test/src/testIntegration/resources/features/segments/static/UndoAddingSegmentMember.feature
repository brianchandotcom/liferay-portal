@spira_Segments @Segments @Static @team_FARO @priority_4
Feature: Undo Adding a Segment Member in the Edit Modal
	As an Business User, I should be able to undo adding a member when editing a Static Segment

	Background: [Setup] Create a Static Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Willa"
		* I click the checkbox on the table row containing "Willa Watsica"
		* I search for "Alyse"
		* I click the checkbox on the table row containing "Alyse Cronin"
		* I click the "Add" button
		* I name the Static segment "UndoAddingSegmentMember - ${Random.1}" and save it

	Scenario: Undo Adding a Segment Member in the Edit Modal
		Given I go to the "Segments" page
		And I search for "UndoAddingSegmentMember - ${Random.1}"
		And I click "UndoAddingSegmentMember - ${Random.1}" in the table
		And I click the "Edit Segment" button
		And I click the "Add Members" button
		And I click the checkbox on the table row containing "Abram Bauch"
		And I click the "Add" button
		And  I click the "View Added Members" button
		When I click the "Undo" button
		And I name the Static segment "UndoAddingSegmentMember - ${Random.1}" and save it
		And I click the "Membership" tab
		Then I should not see an Individual named "Abram Bauch" in the table
@spira_Segments @Segments @List @team_FARO @priority_3
Feature: Segment List kebab menu contains Edit and Delete buttons
	As an Business User, I should see that the Segment List kebab menu contains Edit and Delete buttons

	Background: [Setup] Create a Static Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Abram Bauch"
		* I click the checkbox on the table row containing "Abram Bauch"
		* I click the "Add" button
		* I name the Static segment "AssertSegmentListKebab Segment - ${Random.1}" and save it

	Scenario: Assert Segment List Kebab Menu
		Given I go to the "Segments" page
		When I click the inlined "Kebab" button for the "AssertSegmentListKebab Segment - ${Random.1}" row
		Then I should see the following options in the dropdown menu
			| Edit   |
			| Delete |
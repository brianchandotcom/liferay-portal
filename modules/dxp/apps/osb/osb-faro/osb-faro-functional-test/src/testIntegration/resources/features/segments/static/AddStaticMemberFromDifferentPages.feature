@spira_Segments @Segments @Static @Creation @team_FARO @priority_3
Feature: Add members from different pages
	As a Business User, I should be able to add members from different pages

	Background: [Setup] Navigate to Static Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
	  
	Scenario: Add member from different pages in creation modal
		Given I click the "Add Members" button
		And I click the checkbox on the table row containing "Abram Bauch"
		And I go to page "2" in the creation modal
		And I should be on page "2" in the creation modal
		When I click the checkbox on the table row containing "Ava Wiza"
		And I click the "Add" button
		And I click the "View Added Members" button
		Then I should see an individual named "Abram Bauch" in the table
		And I should see an individual named "Ava Wiza" in the table
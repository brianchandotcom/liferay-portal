@spira_Order @Order @Individuals @List @team_FARO @priority_4
Feature: Sort Individuals List
	As an Business User, I should be able to sort the Individuals Table List

	Background: [Setup] Navigate to the Individuals List page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab
		
	Scenario: Sort by Descending Alphabetical Order
		Given I should see the users sorted in ascending alphabetical order
		When I click the sort order button in the toolbar
		Then I should see the users sorted in descending alphabetical order
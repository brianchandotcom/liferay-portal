@spira_Pagination @Pagination @List @team_FARO @priority_3
Feature: Sorting a List Returns a User to the First Page
	As an Business User, sorting a paginated list should return me to the first page

	Background: [Setup] Go to a page other than the first one in a paginated list
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab
		* I go to page "2" in the table

	Scenario: Sort a List
		Given I should see the "Individuals" page
		Given I should be on page "2" in the table
		When I click the sort order button in the toolbar
		And I should see the users sorted in descending alphabetical order
		Then I should be on page "1" in the table
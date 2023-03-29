@spira_Pagination @Pagination @Individuals @List @team_FARO @priority_3 @blocked
Feature: Individuals Interest details list has pagination
	As a Business User, I should be able to paginate to the second page of individuals interest details list

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Paginate to the second page of individuals interest details
		Given I click the "Interests" tab
		And I click "rich e-commerce" in the table
		When I go to page "2" in the table
		Then I should be on page "2" in the table
		And I should see an element exists in the table